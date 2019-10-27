//app.js
App({
  onLaunch: function() {
    // 展示本地存储能力
    var _that = this
    _that.isTokenUseful = true;
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
          data: {},
          header: {
            'content-type': 'application/json',
            'Authorization': _that.globalData.token
          },
          method: 'post',
          success: function(res) {
            console.log(res.data)
            _that.globalData.wxAccount = res.data.wxAccount
          },
          fail: function(res) {
            //token失效
            _that.isTokenUseful = false
          }
        })
      },
      fail: function(error) {
        console.log("getStorage:" + error.errMsg)
        _that.isTokenUseful = false
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
              if (_that.isTokenUseful == false){
                UserLogin();
              }
            }
          })
        }
      }
    })
  },
  UserLogin: function() {
    //插入登录的用户的相关信息到数据库
    wx.login({
      success(res) {
        if (res.code) {
          //发起网络请求
          wx.request({
            url: this.globalData.urlPath + 'user/login',
            data: JSON.stringify({
              code: res.code,
              userInfo: this.globalData.userInfo
            }),
            method: 'post',
            header: {
              'content-type': 'application/json'
            },
            success: function(res) {
              console.log("插入小程序登录用户信息成功！");
              console.log(res);
              //保存token
              this.globalData.token = res.data.token;
              this.globalData.wxAccount = res.data.account;
              wx.setStorageSync("token", res.data.token);
            },
            fail: function(error) {
              console.log(error);
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    urlPath: 'http://47.98.231.146:8080/api/wx/',
    //token要设置请求头中  
    token: null,
    //代表后端中用户信息，请求用这个 wxOpenid,或者id
    wxAccount: null,
    imgpath: 'http://47.98.231.146:8080/'
  }
})