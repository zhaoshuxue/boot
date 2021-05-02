// miniprogram/pages/pano/index.js
var wxPano = requirePlugin("wxPano")
Page({

  /**
   * 页面的初始数据
   */
  data: {

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
    wxPano.onReady = function () { //wxPano初始化完成后会触发此事件

    }
    wxPano.config({
      panolist: [{
        name: "xindamen",
        src: "https://www.aiotforest.com/pano2048-1024.jpg",
        infospots: [ //信息标记
          {
            type: "modal",
            modal: {
              title: "wxPano",
              content: "欢迎使用wxPano"
            },
            position: {
              x: 0.092,
              y: 0.434
            },
            size: 1,
            icon: "info",
            bindcamera: true,
            bindsize: 0.5,
            bindicon: "info",
            bindopacity: 0.75,
            bindposition: {
              x: 0.5,
              y: 0.75
            }
          },
          {
            type: "page",
            page: function () {
              wx.navigateTo({
                url: "ar",
                success(evt) {
                  console.log(evt);
                }
              })
            },
            position: {
              x: 0.437,
              y: 0.447
            },
            size: 1,
            icon: "info"
          }
        ]
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