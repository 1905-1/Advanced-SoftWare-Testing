// pages/collection/collection.js

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
  onLoad: function(option) {

  },

  onPostTap: function(event) {
    // 获取详情页数据
    var article = event.currentTarget.dataset.article
    var articleStr = JSON.stringify(article)

    // 获取热度
    wx.request({
      url: app.globalData.urlPath + 'article/view',
      data: {
        articleid: article.article.articleId
      },
      header: {
        // 'Authorization': app.globalData.token
      },
      method: 'get',
      success(res) {
        // console.log(res.data.data)
      },
      fail: error => function() {
        console.log(error)
      }
    })

    wx.navigateTo({
      url: '../index/index-detail/index-detail?articleStr=' + encodeURIComponent(articleStr)
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var postsCollected = wx.getStorageSync('post_collected') //获取当前缓存值
    this.setData({
      collected: postsCollected
    })

    var _that = this
    wx.request({
      url: app.globalData.urlPath + 'article/hot',
      data: {
        page: 0,
        size: 10,
        lookUserId: app.globalData.wxAccount.id
      },
      header: {
        'Authorization': app.globalData.token
      },
      method: 'get',
      success(res) {
        for (var i = 0; i < res.data.data.length; i++) {
          res.data.data[i].article.coverimg = (app.globalData.imgPath).slice(0, -1) + res.data.data[i].article.coverimg
          res.data.data[i].article.date = String(res.data.data[i].article.date).split('T')[0]
        }
        _that.setData({
          posts_key: res.data.data
        })
      },
      fail: error => function() {
        console.log(error)
      }
    })
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

  }
})