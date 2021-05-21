// miniprogram/pages/home/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: [],
    totalPage: 1,
    page: 0,
    size: 10,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData()
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

  getData() {
    let _this = this

    var num = this.data.page
    if (num < this.data.totalPage) {
      num += 1
    } else {
      return;
    }

    wx.request({
      method: "GET",
      // url: app.globalData.url + '/wx/studentLeaveList',
      url: 'https://www.funimg.top/funimg/api/funimg/panoList?pageNum=1&pageSize=5',
      success: (res) => {
        console.log(res.data)

        if (res && res.data) {
          var list = res.data.list;

          var arr = _this.data.list;
          if (list) {
            for (var i = 0, len = list.length; i < len; i++) {
              // list[i].checkTime = "请假时间： " + list[i].checkTime.substr(0, 10);
              arr.push(list[i])
            }
            _this.setData({
              list: arr,
              totalPage: res.data.pageSize,
              page: num
            })
            console.log(arr)
          }
        }
      },
      fail: (res) => {
        wx.showModal({
          title: '错误',
          content: '网络连接失败，请检查',
          showCancel: false
        })
      },
      complete: () => {
        // wx.hideLoading()
      }
    })

  },

  showImg(e) {
    console.log(e)
    console.log(e.currentTarget)
    console.log(e.currentTarget.dataset)
    console.log(e.currentTarget.dataset.id)

    var id = e.currentTarget.dataset.id

    wx.navigateTo({
      url: '/pages/pano/index?id=' + id
    })
  }
})