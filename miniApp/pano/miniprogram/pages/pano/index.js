// miniprogram/pages/pano/index.js
var wxPano = requirePlugin("wxPano")
Page({

  /**
   * 页面的初始数据
   */
  data: {
    url: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    this.setData({
      url: options.url + '?imageView2/2/w/1024'
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.initPano(this.data.url)
  },

  initPano: function (url) {
    let _this = this
    wxPano.onReady = function () { //wxPano初始化完成后会触发此事件
      console.log(url)
    }
    wxPano.config({
      panolist: [{
        name: "xindamen",
        // src: "https://www.aiotforest.com/pano2048-1024.jpg",
        // src: "https://highness-1-1253922088.cos.ap-beijing.myqcloud.com/202010temp/04ba6225-24f5-41e0-8a65-fbd773b41b78.jpg",
        src: url
        // src: "https://highness-1-1253922088.cos.ap-beijing.myqcloud.com/202101/ba21c10bfdb345c6ba3804e54586e075.jpg"
      }],
      request: wx.request,
      loader: "GLLoader",
      entryname: "xindamen"
    });
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

  }
})