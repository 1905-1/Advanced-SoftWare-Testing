//index.js
// var postData = require('../../data/posts-data.js')
// var testData = require('../../data/posttest.js')
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {


  },

  search: function(value) {
    return new Promise((resolve, reject) => {
      // setTimeout(() => {
      //   resolve([{
      //     text: '搜索结果',
      //     value: 1
      //   }, {
      //     text: '搜索结果2',
      //     value: 2
      //   }])
      // }, 200)
      wx.request({
        url: app.globalData.urlPath + 'article/like/address',
        data: {
          likename: value,
          lookUserId: app.globalData.wxAccount.id,
          page: 0,
          size: 10
        },
        header: {
          'Authorization': app.globalData.token
        },
        method: 'get',
        success: function (res) {
          // 回调成功执行resolve
          console.log(res)
        },
        fail: function (err) {
          console.log(err)
        },
      })
    })
  },
  selectResult: function(e) {
    console.log('select result', e.detail)
  },

  onPostTap: function(event) {
    // 获取详情页数据
    var article = event.currentTarget.dataset.article;
    console.log(article)

    var articleStr = JSON.stringify(article)
    console.log(articleStr)

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

    // 绑定数据，页面跳转
    wx.navigateTo({
      url: 'index-detail/index-detail?articleStr=' + encodeURIComponent(articleStr)
    })

  },

  adddetail:function(){
    wx.navigateTo({
      url: './add-detail/add-detail',
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
    this.setData({
      // posts_key: postData.postList,
      // test: testData.postList,
      search: this.search.bind(this)
    })

    var _that = this
    wx.request({
      url: app.globalData.urlPath + 'article/hot',
      data: {
        page: 0,
        size: 10,
        lookUserId: app.globalData.wxAccount == null ? -1 : app.globalData.wxAccount.id
      },
      header: {
        // 'Authorization': app.globalData.token
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