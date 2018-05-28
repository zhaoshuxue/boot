// pages/detail/index.js

Page({
  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: '',
    windowWidth: 0,
    dataList: [],
    id: 0,
    hasLast: false,
    hasNext: false,
    lastAlbumId: 0,
    nextAlbumId: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var baseUrl = wx.getStorageSync('baseUrl')
    this.setData({
      baseUrl: baseUrl,
      id: options.id
    })
    this.getData(options.id);
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
    this.getData(this.data.id);
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function (res) {
    return {
      title: '精选趣图',
      path: '/pages/detail/index?id=' + this.data.id,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
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
   * 跳转到其它详情页面
   */
  gotoLast: function (event) {
    wx.navigateTo({
      url: '/pages/detail/index?id=' + this.data.lastAlbumId
    })
  },
  gotoNext: function (event) {
    wx.navigateTo({
      url: '/pages/detail/index?id=' + this.data.nextAlbumId
    })
  },

  /** 
   * 获取详细数据
   */
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
        // console.log(res)
        wx.stopPullDownRefresh()

        if (res.data && res.data.albumData.length > 0) {
          var list = res.data.albumData;

          for (var i = 0, len = list.length; i < len; i++) {
            var imgList = list[i].imgList;
            // list[i].index = i+1;
            for (var j = 0, size = imgList.length; j < size; j++) {
              var img = imgList[j];
              // 当是mp4时需要特殊处理
              if (img.type == 4) {
                // 计算视频要展示的高度
                var a = that.data.windowWidth - 10;
                var h = parseInt(a / img.width * img.height);
                img.nh = "height: " + h + "px";
              }
            }
          }

          that.setData({
            dataList: list
          });

          wx.hideNavigationBarLoading() //完成停止加载

          // 设置当前页面的标题
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
        wx.showModal({
          title: '错误',
          content: '网络连接失败，请检查',
          showCancel: false
        })
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
  }
})