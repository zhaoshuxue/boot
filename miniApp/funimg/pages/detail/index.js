// pages/detail/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    dataList: []
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
    this.setData({
      dataList: [{
        "title": "0501：阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫阿萨德法师法士大夫",
        "imgList": [
          { "imgUrl": "http://highness.qiniudn.com/tiaoqilai.gif", "type": "gif" },
          { "imgUrl": "http://highness.qiniudn.com/20180430002.mp4", "type": "mp4", "width": 222, "height": 242 }
        ]
      }, {
        "title": "0430：阿萨德法师法士大夫",

        "imgList": [
          {
            "imgUrl": "http://ww1.sinaimg.cn/mw690/b11e11f9jw1f48rlsc2sjg20au06n4qv.gif"
          }
        ]
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgList": [
          {
            "imgUrl": "http://ww2.sinaimg.cn/mw690/b11e11f9jw1f48rllabiqg20c806fdz7.gif"
          },
          {
            "imgUrl": "http://ww2.sinaimg.cn/mw690/b11e11f9jw1f48rllabiqg20c806fdz7.gif"
          }
        ]
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgList": [
          {
            "imgUrl": "/images/1.jpg"
          }
        ]
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgList": [
          {
            "imgUrl": "/images/2.jpg"
          }
        ]
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgList": [
          {
            "imgUrl": "/images/3.jpg"
          },
          {
            "imgUrl": "/images/3.jpg"
          }
        ]
      }]
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // 设置当前页面的标题
    wx.setNavigationBarTitle({
      title: 'shuxue'
    })

    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        console.log("屏幕宽度为：" + windowWidth);
        // var h = parseInt(windowWidth / 8.64 * 6)
        // that.setData({
        //   swiperHeight: h
        // });
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

  },

  //
  //
  //

  /**返回上一页 */
  goBack: function () {
    wx.navigateBack()
  },


  imageLoad: function (e) {
    // console.log(e)
  }
})