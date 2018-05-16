// pages/comment/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    baseUrl: '',
    windowWidth: 0,
    imgData: {
      id: 0,
      title: "",
      imgList: []
    },
    commentList: [
      { text: "asdf" },
      { text: "asdf" },
      { text: "asdf" },
      { text: "asdf" },
      { text: "asdf" }
    ],
    id: 0,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var baseUrl = wx.getStorageSync('baseUrl')
    this.setData({
      baseUrl: baseUrl,
      id: options.id
    })
    this.getData(options.id);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    // 查看是否授权
    // wx.getSetting({
    //   success: function (res) {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称
    //       wx.getUserInfo({
    //         success: function (res) {
    //           console(res.userInfo)
    //         }
    //       })
    //     }
    //   }
    // })  
  },

  bindGetUserInfo: function (e) {
    
    console.log(e.detail.userInfo)
    this.setData({
      canIUse: false
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        var windowWidth = res.windowWidth;
        console.log("屏幕宽度为：" + windowWidth);
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
    return {
      title: '精选趣图',
      path: '/pages/comment/index?id=' + this.data.id,
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
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
      url: baseUrl + '/funimg/api/funimg/imgComment',
      data: {
        id: id
      },
      header: {
        'content-type': 'application/json'
      },
      success: (res) => {
        console.log(res)

        if (res.data && res.data.imgData && res.data.imgData.imgList && res.data.imgData.imgList.length > 0) {
          var imgData = res.data.imgData;
          var imgList = imgData.imgList;

          for (var j = 0, size = imgList.length; j < size; j++) {
            var img = imgList[j];
            // console.log(img.type)
            // 当是mp4时需要特殊处理
            if (img.type == 4) {
              // console.log(img)
              // 计算视频要展示的高度
              var a = that.data.windowWidth - 10;
              var h = parseInt(a / img.width * img.height);
              // img.h = h;
              img.nh = "height: " + h + "px";
            }
          }
          

          that.setData({
            imgData: imgData
          });

          //          设置当前页面的标题
          // wx.setNavigationBarTitle({
          //   title: imgData.title
          // })
        }
      },
      fail: (res) => {

      }
    })
  },

  /**返回上一页 */
  goBack: function () {
    wx.navigateBack()
  },
  /** 跳到首页 */
  gotoHome: function () {
    wx.switchTab({
      url: '/pages/everyday/index'
    })
  },

  imageLoad: function (e) {
    // console.log(e)
  }


})