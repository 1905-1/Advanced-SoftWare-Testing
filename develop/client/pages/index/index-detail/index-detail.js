// var postsData = require('../../../data/posts-data.js')

var app = getApp()
var QQMapWX = require('../../../sdk/qqmap-wx-jssdk1.2/qqmap-wx-jssdk.js')

// 实例化API核心类
var qqmapsdk = new QQMapWX({
  key: 'JEBBZ-UIQCU-W5TV5-BKV7O-E3KE2-MQBBH'
});

// 绘制路径
function drawPath(mode, start, dest, _that) {
  if(mode=='driving'){
  qqmapsdk.direction({
    mode: mode,
    //from参数不填默认当前地址
    from: start,
    to: dest,
    success: function (res) {
      console.log(res);
      var coors = res.result.routes[0].polyline
      console.log(coors)
      var pl = [];
      //坐标解压（返回的点串坐标，通过前向差分进行压缩）
      var kr = 1000000;
      for (var i = 2; i < coors.length; i++) {
        coors[i] = Number(coors[i - 2]) + Number(coors[i]) / kr;
      }
      //将解压后的坐标放入点串数组pl中
      for (var i = 0; i < coors.length; i += 2) {
        pl.push({
          latitude: coors[i],
          longitude: coors[i + 1]
        })
      }
      console.log(pl)
      //设置polyline属性，将路线显示出来,将解压坐标第一个数据作为起点
      _that.setData({
        latitude: pl[0].latitude,
        longitude: pl[0].longitude,
        polyline: [{
          points: pl,
          color: '#FF0000DD',
          width: 4
        }]
      })
    },
    fail: function (error) {
      console.error(error);
    },
    complete: function (res) {
      console.log(res);
      console.log(_that.data)
    }
  })
  }
  if(mode=='transit'){
    qqmapsdk.direction({
      mode: mode,
      //from参数不填默认当前地址
      from: start,
      to: dest,
      success: function (res) {
        console.log(res);
        var ret = res.result.routes[0];
        var count = ret.steps.length;
        var pl = [];
        var coors = [];
        var distance = ret.distance / 1000;
        var duration = ret.duration;
        var steps = [];
        //获取各个步骤的polyline
        for (var i = 0; i < count; i++) {
          if (ret.steps[i].mode == 'WALKING' && ret.steps[i].polyline) {
            coors.push(ret.steps[i].polyline);
            steps.push('walking')
          }
          if (ret.steps[i].mode == 'TRANSIT' && ret.steps[i].lines[0].polyline) {
            coors.push(ret.steps[i].lines[0].polyline);
            steps.push(ret.steps[i].lines[0].geton.title + '(' + ret.steps[i].lines[0].title + ')');
            steps.push(ret.steps[i].lines[0].getoff.title + '(' + ret.steps[i].lines[0].title +')');
          }
        }
        //坐标解压（返回的点串坐标，通过前向差分进行压缩）
        var kr = 1000000;
        for (var i = 0; i < coors.length; i++) {
          for (var j = 2; j < coors[i].length; j++) {
            coors[i][j] = Number(coors[i][j - 2]) + Number(coors[i][j]) / kr;
          }
        }
        //定义新数组，将coors中的数组合并为一个数组
        var coorsArr = [];
        for (var i = 0; i < coors.length; i++) {
          coorsArr = coorsArr.concat(coors[i]);
        }
        //将解压后的坐标放入点串数组pl中
        for (var i = 0; i < coorsArr.length; i += 2) {
          pl.push({ latitude: coorsArr[i], longitude: coorsArr[i + 1] })
        }
        //设置polyline属性，将路线显示出来,将解压坐标第一个数据作为起点
        _that.setData({
          latitude: pl[0].latitude,
          longitude: pl[0].longitude,
          polyline: [{
            points: pl,
            color: '#FF0000DD',
            width: 4
          }],
          steps: steps.join("-"),
          distance: String(distance)+'公里',
          duration: String(duration)+'min'
        })
      },
      fail: function (error) {
        console.error(error);
      },
      complete: function (res) {
        console.log(res);
      }
    });
  }
}

// 将地址转换为坐标
function transform(){
  
}

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
    

    var article = JSON.parse(decodeURIComponent(option.articleStr));

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
      'details': article.article.details,
      'address': article.address.desc
    })

    var _that = this

    // 获取当前位置
    wx.getLocation({
      type: "wgs84",
      success: function (res) {
        _that.setData({
          now_latitude: res.latitude,
          now_longitude: res.longitude,
          now_markers: {
            latitude: res.latitude,
            longitude: res.longitude,
            iconPath:'/images/icon/location.png'
          }  
        })
        // 将地址转换为坐标
        qqmapsdk.geocoder({
          //获取表单传入地址
          address: '武汉市' + _that.data.address,
          success: function (res) { //成功后的回调
            var res = res.result;
            var dest_latitude = res.location.lat;
            var dest_longitude = res.location.lng;
            var start = String(_that.data.now_latitude) + ',' + String(_that.data.now_longitude)
            var end = String(dest_latitude) + ',' + String(dest_longitude)
            drawPath('transit', start, end, _that)
          }
        })
      },
      fail: error => function () {
        console.log(error)
      }
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
      var _that = this
      if (this.data.supported) {
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
              supported: !_that.data.supported
            })
          },
          fail: error => function() {
            console.log(error)
          }
        })
      } else {
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
      if (!_that.data.concerned) {
        wx.request({
          url: app.globalData.urlPath + 'add/follow',
          data: {
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
          fail: error => function() {
            console.log(error)
          }
        })
      } else {
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
          fail: error => function() {
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