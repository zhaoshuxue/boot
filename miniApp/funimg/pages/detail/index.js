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
        "imgUrl": "http://highness.qiniudn.com/tiaoqilai.gif"
      }, {
        "title": "0430：阿萨德法师法士大夫",
        "imgUrl": "http://ww1.sinaimg.cn/mw690/b11e11f9jw1f48rlsc2sjg20au06n4qv.gif"
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgUrl": "http://ww2.sinaimg.cn/mw690/b11e11f9jw1f48rllabiqg20c806fdz7.gif"
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgUrl": "/images/1.jpg"
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgUrl": "/images/2.jpg"
      }, {
        "title": "0429：阿萨德法师法士大夫",
        "imgUrl": "/images/3.jpg"
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


  imageLoad: function(e){
    // console.log(e)
  }
})