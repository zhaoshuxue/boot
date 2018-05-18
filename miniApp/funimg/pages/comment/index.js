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
    commentList: [],
    id: 0,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    userInfo: null,
    openid: '',
    toOpenid: '',
    toNickName: '',
    releaseFocus: false,
    placeholderText: "评论",
    commentValue: '',
    submitDisabled: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var baseUrl = wx.getStorageSync('baseUrl')
    var userInfo = wx.getStorageSync('userInfo')
    this.setData({
      baseUrl: baseUrl,
      id: options.id,
      userInfo: userInfo
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
      userInfo: e.detail.userInfo
    })
    wx.setStorageSync('userInfo', e.detail.userInfo)
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
//  获取用户的openid
    this.getOpenId()
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

        if (res.data && res.data.comments && res.data.comments.length > 0){
          that.setData({
            commentList: res.data.comments
          });
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
  },

  /**
  * 点击回复
  */
  bindReply: function (e) {
    this.setData({
      releaseFocus: true
    })
  },

  // 
  replyUser: function (event){
    var openid = event.currentTarget.dataset.openid;
    var nickName = event.currentTarget.dataset.name;
    // console.log(openid)
    // console.log(nickName)

    console.log(this.data.userInfo)

    this.setData({
      releaseFocus: true,
      toOpenid: openid,
      toNickName: nickName,
      placeholderText: "回复" + nickName + "："
    })
  },

  formSubmit: function(e){

    console.log('formSubmit')

    var baseUrl = this.data.baseUrl
    var that = this;

    console.log(that.data.userInfo)

    var openid = that.data.openid;
    if (openid == '') {
      wx.showModal({
        title: '错误',
        content: '您未登录，请退出后重新操作',
        showCancel: false
      })
      return;
    }
    var toOpenid = that.data.toOpenid;
    var nickName = that.data.userInfo.nickName;
    var head = that.data.userInfo.avatarUrl;
    if (nickName == '') {
      wx.showModal({
        title: '提示',
        content: '您未登录，请点击授权登录',
        showCancel: false
      })
      that.setData({
        userInfo: null
      })
      return;
    }
    var text = that.data.commentValue;
    var id = that.data.id;


    

    

    

    // return;

    wx.request({
      url: baseUrl + '/funimg/api/funimg/addComment',
      method: 'POST',
      data: {
        openid: openid,
        toOpenid: toOpenid,
        nickName: nickName,
        head: head,
        text: text,
        id: id
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded'
      },
      success: function (res) {
        console.log(res)

        

        if (res.data && res.data.code == 200) {
          // wx.showToast({
          //   title: '评论成功' // res.data.message
          // })

          let commentList = that.data.commentList;
          // console.log(commentList)

          let newComment = {
            fromUser: openid,
            headImg: head,
            nickName: nickName,
            text: text,
            toNickName: that.data.toNickName,
            toUser: toOpenid
          }

          commentList.push(newComment)

          that.setData({
            releaseFocus: false,
            toOpenid: '',
            toNickName: '',
            placeholderText: "评论",
            commentList: commentList,
            commentValue: '',
            submitDisabled: true
          })

//        滚动到最底
          wx.createSelectorQuery().select('#commentID').boundingClientRect(function (rect) {
            // var res = wx.getSystemInfoSync()
            // if (rect.height > res.screenHeight){
              wx.pageScrollTo({
                scrollTop: rect.height
              })
            // }
          }).exec()
        }
      },
      fail: function (res) {
        wx.showModal({
          title: '评论失败',
          content: '网络连接失败，请检查',
          showCancel: false
        })
      }
    })
  },


  bindinputFun: function(e){
    // console.log(e)

    var value = e.detail.value, len = parseInt(value.length);
    if (len == 0){
      this.setData({
        submitDisabled: true
      })
      return;
    }

    this.setData({
      commentValue: value,
      submitDisabled: false
    })
  },

  /**
   * 获取openid
   */
  getOpenId: function () {
    var that = this;
    var openid = wx.getStorageSync('openid')
    console.info('openid : ' + openid)
    if (openid == '') {
      var baseUrl = this.data.baseUrl
      var code = wx.getStorageSync('login_code');
      if (code != '') {
        wx.request({
          url: baseUrl + '/funimg/api/funimg/getOpenId',
          data: {
            code: code
          },
          header: {
            'content-type': 'application/json'
          },
          success: (res) => {
            // console.log(res)
            if (res.data) {
              // 保存openid，用于用户的收藏操作
              wx.setStorageSync('openid', res.data.data)
              that.setData({
                openid: res.data.data
              })
            }
          }
        })
      }
    }else{
      this.setData({
        openid: openid
      })
    }
  }


})