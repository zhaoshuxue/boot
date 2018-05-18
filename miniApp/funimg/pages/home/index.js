// pages/detail/index.js

Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: '',
    windowWidth: 0,
    params: {
      pageNum: 1,
      pageSize: 6
    },
    pages: 0,
    dataList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var baseUrl = wx.getStorageSync('baseUrl')
    this.setData({
      baseUrl: baseUrl
    })
    // 开始加载数据
    this.loadData(1)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var that = this;
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        console.log("屏幕宽度为：" + windowWidth);
        that.setData({
          windowWidth: windowWidth
        });
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    // 上拉加载更多的数据
    var pages = this.data.pages;
    var pageNum = this.data.params.pageNum;
    if (pageNum < pages) {
      this.loadData(pageNum + 1);
      if (pageNum + 1 == pages) {
        // this.setData({
        //   nomore: false
        // })
      }
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  // ******************************************
  // 自定义函数
  // ******************************************

  /**
   * 跳转到图片评论页面
   */
  gotoImage: function (event) {
    var id = event.currentTarget.dataset.id;
    console.log(id)
    wx.navigateTo({
      url: '/pages/comment/index?id=' + id
    })
  },

  /**
   * 加载数据
   */
  loadData: function (pageNum) {
    var baseUrl = this.data.baseUrl
    var that = this;

    wx.showLoading({
      title: '努力加载中'
    })
    if (pageNum == 1) {
      // 第一次加载时，把没有更多信息的提示隐藏掉
      // that.setData({
      //   nomore: true
      // })
    }

    var $param = this.data.params;

    $param.pageNum = pageNum

    this.setData({
      params: $param
    })

    // console.log('搜索参数为：')
    // console.log($param)

    wx.request({
      url: baseUrl + '/funimg/api/funimg/hotImgs',
      method: 'GET',
      data: $param,
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res)

        var list = res.data.list;
        if (list != null && list.length > 0) {
          for (var i = 0, len = list.length; i < len; i++) {
            var imgList = list[i].imgList;
            for (var j = 0, size = imgList.length; j < size; j++) {
              var img = imgList[j];
              // 当是mp4时需要特殊处理
              if (img.type == 4) {
                // console.log(img)
                // 计算视频要展示的高度
                var a = that.data.windowWidth - 10;
                var h = parseInt(a / img.width * img.height);
                img.nh = "height: " + h + "px";
              }
            }
          }

          var newDataList = list;
          if (pageNum > 1) {
            let dataList = that.data.dataList;
            // 追加到已有数据后面
            newDataList = dataList.concat(list);
          }
          that.setData({
            dataList: newDataList
          });
        } else {
          // 没有数据则清空列表
          that.setData({
            dataList: []
          })
          wx.showModal({
            title: '',
            content: '没有查到符合条件的数据，请修改过滤条件后重试', // todo
            showCancel: false
          })
        }

        wx.hideLoading()
        console.log("总页数: " + res.data.pages)

        that.setData({
          pages: res.data.pages
        })
      },
      fail: function (res) {
        wx.hideLoading()
        wx.showModal({
          title: '错误',
          content: '网络连接失败，请检查',
          showCancel: false
        })
      }
    })
  },

  getData: function (id) {
    var that = this;
    var baseUrl = that.data.baseUrl;
    wx.request({
      url: baseUrl + '/funimg/api/funimg/albumData',
      data: {
        albumId: id
      },
      header: {
        'content-type': 'application/json'
      },
      success: (res) => {
        console.log(res)

        if (res.data && res.data.albumData.length > 0) {
          var list = res.data.albumData;

          for (var i = 0, len = list.length; i < len; i++) {
            var imgList = list[i].imgList;
            // list[i].index = i+1;
            for (var j = 0, size = imgList.length; j < size; j++) {
              var img = imgList[j];
              // console.log(img.type)
              // 当是mp4时需要特殊处理
              if (img.type == 4) {
                // console.log(img)
                // 计算视频要展示的高度
                var a = that.data.windowWidth - 10;
                var h = parseInt(a / img.width * img.height);
                // img.h = h;
                img.nh = "height: " + h + "px";
              }
            }
          }

          that.setData({
            dataList: list
          });

          //          设置当前页面的标题
          wx.setNavigationBarTitle({
            title: res.data.title
          })

          let lastAlbumId = res.data.lastAlbumId;
          let nextAlbumId = res.data.nextAlbumId;

          let hasLast = false;
          if (lastAlbumId){
            hasLast= true;
          }

          let hasNext = false;
          if (nextAlbumId) {
            hasNext = true;
          }

          that.setData({
            hasLast: hasLast,
            hasNext: hasNext,
            lastAlbumId: lastAlbumId,
            nextAlbumId: nextAlbumId
          });


        }
      },
      fail: (res) => {

      }
    })
  },

  /**返回上一页 */
  goBack: function () {
    wx.navigateBack()
  },
  /** 跳到首页 */ 
  gotoHome: function(){
    wx.switchTab({
      url: '/pages/everyday/index'
    })
  },

  imageLoad: function (e) {
    // console.log(e)
  },


  /**
   * 获取openid
   */
  getOpenId: function () {
    var that = this;
    var openid = wx.getStorageSync('openid')
    console.info('openid : ' + openid)
    if (openid == '') {
      var baseUrl = this.data.baseUrl
      var code = wx.getStorageSync('login_code');
      if (code != '') {
        wx.request({
          url: baseUrl + '/funimg/api/funimg/getOpenId',
          data: {
            code: code
          },
          header: {
            'content-type': 'application/json'
          },
          success: (res) => {
            // console.log(res)
            if (res.data) {
              // 保存openid，用于用户的收藏操作
              wx.setStorageSync('openid', res.data)
            }
          }
        })
      }
    }
  }
})