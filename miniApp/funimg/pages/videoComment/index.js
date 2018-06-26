// pages/videoComment/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: '',
    id: 0,
    url: "",
    height: 0,
    width: 0,
    windowWidth: 0,
    windowHeight: 0,
    videoHeight: '',
    nextId: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // console.log(options.id)
    // console.log(options.url)
    // console.log(options.title)
    // console.log(options.height)
    // console.log(options.width)

    var baseUrl = wx.getStorageSync('baseUrl')
    this.setData({
      baseUrl: baseUrl,
      id: options.id,
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
   * 
   */
  getData: function (id) {
    var that = this;
    var baseUrl = that.data.baseUrl;
    wx.request({
      url: baseUrl + '/funimg/api/funimg/videoDetail',
      data: {
        id: id
      },
      header: {
        'content-type': 'application/json'
      },
      success: (res) => {
        // console.log(res)
        var data = res.data;

        // 设置当前页面的标题
        wx.setNavigationBarTitle({
          title: data.title
        })

        that.setData({
          url: data.linkUrl,
          height: data.height,
          width: data.width,
          nextId: data.nextId
        })

        that.showVideo()
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

  /**
   * 显示
   */
  showVideo: function () {
    var that = this;
    var width = that.data.width
    var height = that.data.height
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        var windowHeight = res.windowHeight;
        // console.log("屏幕宽度为：" + windowWidth);
        // console.log("屏幕高度为：" + windowHeight);

        var h = parseInt(windowWidth / width * height);
        if (h > windowHeight) {
          h = windowHeight
        }

        // h -= 10;

        var videoHeight = "height: " + h + "px";

        // console.log("计算视频高度为：" + videoHeight);

        that.setData({
          windowWidth: windowWidth,
          windowHeight: windowHeight,
          videoHeight: videoHeight
        });
      }
    })
  },


  videoEnd: function(){
    var that = this;
    console.info('播放结束后2秒自动播放下一个')
    setTimeout(function(){
      that.playNext()
    }, 2000)
  },


  playNext: function(){
    var that = this;
    var nextId = that.data.nextId
    if (nextId != null && nextId != 0){
      wx.navigateTo({
        url: '/pages/videoComment/index?id=' + nextId
      })
    }
  }
})