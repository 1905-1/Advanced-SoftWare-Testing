// pages/attention/attention.js
// var postData = require('../../data/users-data.js')

var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    
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
    
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var _that = this
    wx.request({
      url: app.globalData.urlPath + 'get/follow',
      data:{
        id: app.globalData.wxAccount.id
      },
      header:{
        'Authorization': app.globalData.token
      },
      method: 'get',
      success(res) {
        _that.setData({
          posts_key: res.data.data
        })
      },
      fail: error => function () {
        console.log(error)
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
    
  }
})