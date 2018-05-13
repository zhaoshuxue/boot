// pages/everyday/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dataList: [{
      "title": "0501：阿萨德法师法士大夫",
      "imgUrl": "http://ww2.sinaimg.cn/mw690/b11e11f9jw1f48rlmqbeig20ak06o4qr.gif"
    }, {
      "title": "0430：阿萨德法师法士大夫",
      "imgUrl": "http://ww1.sinaimg.cn/mw690/b11e11f9jw1f48rlsc2sjg20au06n4qv.gif"
    }, {
      "title": "0429：阿萨德法师法士大夫",
      "imgUrl": "http://ww2.sinaimg.cn/mw690/b11e11f9jw1f48rllabiqg20c806fdz7.gif"
    }]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.videoContext = wx.createVideoContext('myVideo')
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

  // **************************************
  // ************ 自定义方法 ***************
  // **************************************

  /**
   * 跳转到详情页面
   */
  gotoDetail: function (event) {
    var id = event.currentTarget.dataset.id;
    wx.navigateTo({
      url: '/pages/detail/index?id=' + id
    })
  },

  videoLoading: function(){
    console.log(new Date())
  },
  videoError: function () {
    console.log(222)
  }
})