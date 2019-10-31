// var postsData = require('../../../data/posts-data.js')

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
    // var articleId = option.articleId
    // var content = option.content
    // var coverimg = option.coverimg
    // var date = option.date
    // var fee = option.fee
    // var playtime = option.playtime
    // var title = option.title
    // var avatarUrl = option.avatarUrl
    // var nickName = option.nickName
    // var authorId = parseInt(option.authorId)
    // var supported = (option.issupport =='true')
    // var concerned = (option.isconcerned == 'true')
    // var details = option.details
    console.log(option.articleStr)
    var article = JSON.parse(decodeURIComponent(option.articleStr));

    console.log(article)
  
    this.setData({
      'articleId': article.article.articleId,
      'authorId': article.authorId,
      'date': article.article.date,
      'fee': article.article.fee,
      'playtime': article.article.playtime,
      'title': article.article.title,
      'avatarUrl': article.avatarUrl,
      'nickName': article.nickName,
      'content': article.article.content,
      'coverimg': article.article.coverimg,
      'supported': article.issupport,
      'concerned': article.isconcerned,
      'details': article.article.details
    })

    var postCollected = wx.getStorageSync('post_collected')
    if (postCollected) {
      var postCollected = postCollected[this.data.articleId]
      this.setData({
        collected: postCollected
      })
    } else {
      var postCollected = {}
      postCollected[this.data.articleId] = false
      wx.setStorageSync('post_collected', postCollected)
    }
  },

  onCollectionTap: function(event) {
    if (app.globalData.wxAccount != null) {
      var postsCollected = wx.getStorageSync('post_collected') //获取当前缓存值
      var postCollected = postsCollected[this.data.articleId]
      postCollected = !postCollected //收藏变成未收藏，未收藏变成收藏
      postsCollected[this.data.articleId] = postCollected //修改文章收藏状态缓存值
      wx.setStorageSync('post_collected', postsCollected) //更新文章是否收藏缓存值
      //更新数据绑定变量，从而实现切换图片
      this.setData({
        collected: postCollected
      })
      wx.showToast({
        title: postCollected ? '收藏成功' : '取消成功',
      })
    }
  },
  onSupportTap: function(event) {
    if (app.globalData.wxAccount != null) {
      console.log(this.data)
      var _that = this
      if (this.data.supported){
        wx.request({
          url: app.globalData.urlPath + 'article/delete/support',
          data: {
            id: app.globalData.wxAccount.id,
            articleid: _that.data.articleId
          },
          header: {
            'Authorization': app.globalData.token
          },
          method: 'get',
          success(res) {
            _that.setData({
              supported:!_that.data.supported
            })
          },
          fail: error => function () {
            console.log(error)
          }
        })
      }

      else{
      wx.request({
        url: app.globalData.urlPath + 'article/add/support',
        data: {
          id: app.globalData.wxAccount.id,
          articleid: _that.data.articleId
        },
        header: {
          'Authorization': app.globalData.token
        },
        method: 'get',
        success(res) {
          _that.setData({
            supported: !_that.data.supported
          })
        },
        fail: error => function() {
          console.log(error)
        }

      })
      }
    }
  },

  onConcernTap: function(event) {
    if (app.globalData.wxAccount != null) {
      var _that = this
      if (!_that.data.concerned){
      wx.request({
        url: app.globalData.urlPath + 'add/follow',
        data:{
          id: app.globalData.wxAccount.id,
          followid: this.data.authorId
        },
        header: {
          'Authorization': app.globalData.token
        },
        method: 'get',
        success(res) {
          _that.setData({
            concerned: !_that.data.concerned
          })
        },
        fail: error => function () {
          console.log(error)
        }
      }) 
    }
  
    else{
      wx.request({
        url: app.globalData.urlPath + 'delete/follow' + '?id=' + app.globalData.wxAccount.id + '&followid=' + this.data.authorId,
        data: {
          // id: app.globalData.wxAccount.id,
          // followid: this.data.authorId
        },
        header: {
          'Authorization': app.globalData.token
        },
        method: 'delete',
        success(res) {
          _that.setData({
            concerned: !_that.data.concerned
          })

        },
        fail: error => function () {
          console.log(error)
        }
      })
    }
      wx.showToast({
        title: this.data.concerned ? '取消成功' : '关注成功',
      })
    }
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