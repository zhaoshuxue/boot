// pages/detail/index.js

var tableData = require('../../data/detailList.js');

Page({

  /**
   * 页面的初始数据
   */
  data: {
    windowWidth: 0,
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
    var that = this;

    let list = tableData.dataList;
    // console.log(list)

    for(var i=0,len=list.length; i<len;i++){
      var obj = list[i];
      var imgList = obj.imgList;
      // console.log(imgList)
      for(var j=0,size=imgList.length; j<size; j++){
        var img = imgList[j];
        // console.log(img.type)
        // 当是mp4时需要特殊处理
        if(img.type == 'mp4'){
          // console.log(img)
          // 计算视频要展示的高度
          var a = that.data.windowWidth * 2 - 10 * 2;
          var h = parseInt(a / img.width * img.height);
          // img.h = h;
          img.nh = "height: "+h+"rpx";
        }
      }
    }
    
    // console.log(list)
    // 初始化数据
    this.setData({
      dataList: list
    });

    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    // 设置当前页面的标题
    wx.setNavigationBarTitle({
      title: 'shuxue'
    })
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        console.log("屏幕宽度为：" + windowWidth);
        // var h = parseInt(windowWidth / 8.64 * 6)
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