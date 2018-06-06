// pages/videoComment/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    url: "",
    height: 0,
    width: 0,
    windowWidth: 0,
    windowHeight: 0,
    videoHeight: ''
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

    // 设置当前页面的标题
    wx.setNavigationBarTitle({
      title: options.title
    })

    this.setData({
      url : options.url,
      height: options.height,
      width: options.width
    })


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
    var width = that.data.width
    var height = that.data.height
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        var windowHeight = res.windowHeight;
        // console.log("屏幕宽度为：" + windowWidth);
        // console.log("屏幕高度为：" + windowHeight);

        var h = parseInt(windowWidth / width * height);
        if (h > windowHeight){
          h = windowHeight
        }
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
  
  }
})