// pages/everyday/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: '',
    params: {
      pageNum: 1,
      pageSize: 6
    },
    pages: 0,
    dataList: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var baseUrl = wx.getStorageSync('baseUrl')
    this.setData({
      baseUrl: baseUrl
    })

    // 开始加载数据
    this.loadData(1)

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

  /**
   * 加载数据
   */
  loadData: function (pageNum) {
    var baseUrl = this.data.baseUrl
    var that = this;

    wx.showLoading({
      title: '努力加载中'
    })
    if (pageNum == 1) {
      // 第一次加载时，把没有更多信息的提示隐藏掉
      // that.setData({
      //   nomore: true
      // })
    }

    var $param = this.data.params;

    $param.pageNum = pageNum

    this.setData({
      params: $param
    })

    // console.log('搜索参数为：')
    // console.log($param)

    wx.request({
      url: baseUrl + '/funimg/api/funimg/lists',
      method: 'GET',
      data: $param,
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res)

        var list = res.data.list;
        if (list != null && list.length > 0) {

          // for (var i = 0, len = list.length; i < len; i++) {
          //   if (list[i].imgUrl == null || list[i].imgUrl == '') {
          //     list[i].imgUrl = '/images/noimage.jpg'
          //   }
          // }

          var newDataList = list;
          if (pageNum > 1) {
            let dataList = that.data.dataList;
            // 追加到已有数据后面
            newDataList = dataList.concat(list);
          }
          that.setData({
            dataList: newDataList
          });
        } else {
          // 没有数据则清空列表
          that.setData({
            dataList: []
          })
          wx.showModal({
            title: '',
            content: '没有查到符合条件的数据，请修改过滤条件后重试', // todo
            showCancel: false
          })
        }

        wx.hideLoading()
        console.log("总页数: " + res.data.pages)

        that.setData({
          pages: res.data.pages
        })
      },
      fail: function (res) {
        wx.hideLoading()
        wx.showModal({
          title: '错误',
          content: '网络连接失败，请检查',
          showCancel: false
        })
      }
    })
  },

  videoLoading: function(){
    console.log(new Date())
  },
  videoError: function () {
    console.log(222)
  }
})