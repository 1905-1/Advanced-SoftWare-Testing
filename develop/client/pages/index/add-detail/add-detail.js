// pages/index/add-detail/add-detail.js
var util = require('../../../utils/util.js')
var app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    images: [],

  },

  // 获取标题
  title: function(e) {
    this.setData({
      title: e.detail.value,
    })
  },

  // 选择图片
  chooseImage: function(e) {
    var _that = this
    wx.chooseImage({
      sizeType: ['original', 'compressed'], // 可选择原图或压缩后的图片,
      sourceType: ['album', 'camera'], // 可选择性开放访问相册、相机
      success: function(res) {
        const images = _that.data.images.concat(res.tempFilePaths)
        // 限制最多只能留下3张照片
        _that.data.images = images.length == 1 ? images : images.slice(0, 1)
        console.log(_that.data.images)
        _that.setData({
          choosed: true
        })
      },
      fail: function(res) {
        _that.setData({
          choosed: false
        })
      },
      complete: function(res) {
        wx.showToast({
          title: _that.data.choosed ? '添加成功' : '添加失败',
        })
      },
    })
  },

  // 获取文章详情
  detail: function(e) {
    this.setData({
      detail: e.detail.value,
      content: e.detail.value.slice(0, 50)
    })
  },

  // 获取人均消费
  fee: function(e) {
    this.setData({
      fee: Number(e.detail.value)
    })
  },

  // 获取游玩时间
  playtime: function(e) {
    this.setData({
      playtime: Number(e.detail.value)
    })
  },

  // 提交数据
  postArticle: function(e) {
    var TIME = util.formatTime(new Date());
    this.setData({
      addressId: "WH004",
      date: TIME,
      supportnum: 0,
      hotnum: 0,
      id: app.globalData.wxAccount.id,
      addressId: "WH004"
    })

    console.log(this.data);
    console.log(this.data.images[0])
    // 上传数据
    var _that = this
    wx.uploadFile({
      url: app.globalData.urlPath + 'article/add',
      filePath: _that.data.images[0],
      name: 'img',
      header: {
        'Authorization': app.globalData.token
      },
      formData: {
        articleId: 0,
        date: _that.data.date,
        coverimg: _that.data.images[0],
        content: _that.data.content,
        supportnum: _that.data.supportnum,
        fee: _that.data.fee,
        playtime: _that.data.playtime,
        hotnum: _that.data.hotnum,
        id: app.globalData.wxAccount.id,
        addressId: "WH004",
        details: _that.data.detail,
        title: _that.data.title
      },
      success(res) {
        console.log(res);
        if (res.statusCode == 200){
          _that.setData({
            uploaded: true
          })
        }
        else{
          _that.setData({
            uploaded: false
          })
        }
      },
      complete: function (res) {
        wx.showToast({
          title: _that.data.uploaded ? '发布成功' : '发布失败',
        })
      },
    })



  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {


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