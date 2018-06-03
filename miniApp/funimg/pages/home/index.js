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
      pageSize: 5
    },
    pages: 0,
    nomore: true,
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
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        // console.log("屏幕宽度为：" + windowWidth);
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
    wx.showNavigationBarLoading() //在标题栏中显示加载
    this.loadData(1)
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
        this.setData({
          nomore: false
        })
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

    var $param = this.data.params;

    $param.pageNum = pageNum

    this.setData({
      params: $param
    })

    wx.request({
      url: baseUrl + '/funimg/api/funimg/hotImgs',
      method: 'GET',
      data: $param,
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        // console.log(res)
        wx.stopPullDownRefresh()
        wx.hideNavigationBarLoading() //完成停止加载
        
        var list = res.data.list;
        if (list != null && list.length > 0) {
          for (var i = 0, len = list.length; i < len; i++) {
            var imgList = list[i].imgList;
            for (var j = 0, size = imgList.length; j < size; j++) {
              var img = imgList[j];
              // 当是mp4时需要特殊处理
              if (img.type == 4) {
                // 计算视频要展示的高度
                // var a = that.data.windowWidth - 10;
                var a = that.data.windowWidth * 0.96;
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
        }

        wx.hideLoading()
        // console.log("总页数: " + res.data.pages)
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

  imageLoad: function (e) {
    // console.log(e)
  }

})