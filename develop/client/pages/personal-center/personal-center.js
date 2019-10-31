// pages/user/user.js
//获取应用实例
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
      // console.log(app.globalData.userInfo);
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {
    
  },
  queryUsreInfo: function () {
    wx.request({
      url: app.globalData.urlPath + 'sayHello',
      data: {
        openid: app.globalData.openid
      },
      header: {
        'content-type': 'application/json',
        'token': app.globalData.token
      },
      success: function (res) {
        getApp().globalData.userInfo = res.data;
      }
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    if (typeof this.getTabBar === 'function' &&
      this.getTabBar()) {
      this.getTabBar().setData({
        selected: 3
      })
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  bindGetUserInfo: function(e) {
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
      var that = this;
      app.globalData.userInfo = e.detail.userInfo

      //插入登录的用户的相关信息到数据库
      wx.login({
        success(res) {
          if (res.code) {
            //发起网络请求
            wx.request({
              url: app.globalData.urlPath + 'user/login',
              data: JSON.stringify({
                code: res.code,
                userInfo: app.globalData.userInfo
              }),
              method:'post',
              header: {
                'content-type': 'application/json'
              },
              success: function(res) {
                console.log("插入小程序登录用户信息成功！");
                console.log(res);
                //保存token
                app.globalData.token = res.data.token;
                app.globalData.wxAccount = res.data.account;
                wx.setStorageSync("token", res.data.token);
                that.setData({
                  hasUserInfo:true,
                  userInfo: app.globalData.userInfo
                })
              },
              fail: function(error){
                console.log(error);
              }
            })
          } else {
            console.log('登录失败！' + res.errMsg)
          }
        }
      })

    } else {
      //用户按了拒绝按钮
      wx.showModal({
        title: '警告',
        content: '您点击了拒绝授权，将无法进入小程序，请授权之后再进入!!!',
        showCancel: false,
        confirmText: '返回授权',
        success: function(res) {
          if (res.confirm) {
            console.log('用户点击了“返回授权”')
          }
        }
      })
    }
  }
})