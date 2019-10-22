//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var _that = this
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)
    var token = wx.getStorage({
      key: 'token',
      success: function(res) {
        console.log(res.data)
        _that.globalData.token = res.data
        //如果有token直接请求userinfo
        wx.request({
          url: _that.globalData.urlPath + 'user/userinfo',
          data: {
          },
          header: {
            'content-type': 'application/json',
            'token': _that.globalData.token
          },
          method: 'post',
          success: function (res) {
            console.log(res.data);
            _that.globalData.wxAccout = res.data.wxAccout;
          }
        })
      },
      fail: function (error){
        console.log("getStorage:"+error.errMsg)
      }
    })

    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo
              console.log(res.userInfo)
              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }

              //如果没有token，用户已经授权登录的情况下，就请求token
              
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    urlPath:'http://127.0.0.1:8080/api/wx/',
    //token要设置请求头中  
    token:null,
    //代表后端中用户信息，请求用这个 wxOpenid,或者id
    wxAccout:null
  }
})