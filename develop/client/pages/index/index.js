//index.js
var postData = require('../../data/posts-data.js')
var testData = require('../../data/posttest.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    test:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      posts_key: postData.postList,
      test: testData.postList,
      search: this.search.bind(this)
    })
  },

  search: function (value) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve([{ text: '搜索结果', value: 1 }, { text: '搜索结果2', value: 2 }])
      }, 200)
    })
  },
  selectResult: function (e) {
    console.log('select result', e.detail)
  },

  onPostTap:function(event){
    var postId = event.currentTarget.dataset.postid;
    var authorId = event.currentTarget.dataset.authorid;
    // console.log("on post id is" + postId);
    wx.navigateTo({
      url: 'index-detail/index-detail?postId=' + postId + '&authorId=' + authorId,
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
    // console.log(this.data.test[0]);
    // for (var i = 0; i < this.data.test.length; i++) {
    //   wx.uploadFile({
    //     url: app.globalData.urlPath + 'article/add',
    //     filePath: this.data.test[i].coverimg,
    //     name: "img",
    //     header:{
    //       'Authorization': app.globalData.token
    //     },
    //     formData: this.data.test[i],
    //     success(res) {
    //       console.log(res);
    //     }
    //   })
    // }
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