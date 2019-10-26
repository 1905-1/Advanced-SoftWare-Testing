var postsData = require('../../../data/posts-data.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (option) {
    var postId = option.postId   //获取文章id
    this.data.currentPostId = postId  // 放入data中，方便后续函数调用
    var postData = postsData.postList[postId]
    var authorId = option.authorId // 取用户id
    this.data.currentAuthorId = authorId
    this.setData(postData)
  
    var postCollected = wx.getStorageSync('post_collected')
    var postSupported = wx.getStorageSync('post_supported')
    var postConcerned = wx.getStorageSync('post_concerned')
    if(postCollected){
      var postCollected = postCollected[postId]
      this.setData({
        collected: postCollected
      })
    }
    else{
      var postCollected = {}
      postCollected[postId] = false
      wx.setStorageSync('post_collected', postCollected)
    }
    if(postSupported){
      var postSupported = postSupported[postId]
      this.setData({
        supported: postSupported
      })
    }
    else{
      var postSupported = {}
      postSupported[postId] = false
      wx.setStorageSync('post_supported', postSupported)
    }
    if(postConcerned){
      var postConcerned = postConcerned[authorId]
      this.setData({
        concerned: postConcerned
      })
    }
    else{
      var postConcerned = {}
      postConcerned[authorId] = false
      wx.setStorageSync('post_concerned', postConcerned)
    }

  },

  onCollectionTap:function(event){
    var postsCollected = wx.getStorageSync('post_collected') //获取当前缓存值
    var postCollected = postsCollected[this.data.currentPostId]
    postCollected = !postCollected //收藏变成未收藏，未收藏变成收藏
    postsCollected[this.data.currentPostId] = postCollected //修改文章收藏状态缓存值
    wx.setStorageSync('post_collected', postsCollected)  //更新文章是否收藏缓存值
    //更新数据绑定变量，从而实现切换图片
    this.setData({
      collected: postCollected
    })
    wx.showToast({
      title: postCollected?'收藏成功':'取消成功',
    })
  },

  onSupportTap:function(event){
    var postsSupported = wx.getStorageSync('post_supported')
    var postSupported = postsSupported[this.data.currentPostId]
    postSupported = !postSupported
    postsSupported[this.data.currentPostId] = postSupported
    wx.setStorageSync('post_supported', postsSupported)
    this.setData({
      supported: postSupported
    })
  },

  onConcernTap:function(event){
    var postsConcerned = wx.getStorageSync('post_concerned')
    var postConcerned = postsConcerned[this.data.currentAuthorId]
    postConcerned = !postConcerned
    postsConcerned[this.data.currentAuthorId] = postConcerned
    wx.setStorageSync('post_concerned', postsConcerned)
    this.setData({
      concerned: postConcerned
    })
    wx.showToast({
      title: postConcerned ? '关注成功' : '取消成功',
    })
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
    
  }
})