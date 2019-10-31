# 服务端

> 请求地址在app.globalData.urlPath <br>
> 请求到的图片路径统一在前面+app.globalData.imgPath
## WxApplet 用户接口
---
### 1.登录接口 
返回token+wxAccout（token+用户基本信息），如果已经登录返回用户基本信息。并且会更新用户基本信息
#### 获取方式
Http POST
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/user/login <br>
示例:
> http://127.0.0.1:8080/api/wx/user/login
#### 请求参数
变量名|描述|变量值
-|-|-
code|wx.login 返回的code 微信端返回的校验字符串|String
userInfo|wx点击授权后返回的userInfo 微信用户信息|JSON
#### 返回参数和数值说明
变量名|描述|变量值
-|-|-
token|用户服务端请求的安全校验code|String
account|返回的userInfo 用户信息|JSON

account 说明

变量名|描述|变量值
-|-|-
avatarUrl|用户头像地址|String
city|城市|String
country|国家|String
gender|性别|int 1为男 0为女
id|用户id|int
language|语言|String
lastTime|最后一次登录时间|date
nickname|昵称|String
province|省|String

### 2.获取用户信息
返回wxAccout（用户基本信息
#### 获取方式
Http POST
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/user/userinfo <br>
示例:
> http://127.0.0.1:8080/api/wx/user/userinfo
#### 请求参数
需要在httphead添加token
>'Authorization': _that.globalData.token

```
代码示例：
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
```

返回参数如api 1 但不返回token

### 3.关注用户
关注用户
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/add/follow <br>
示例:
> http://127.0.0.1:8080/api/wx/add/follow?id=1&followid=2
#### 请求参数
需要在httphead添加token
id 为关注者的ID
followid 为被关注者的id

### 4.取消关注用户
取消关注用户
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/delete/follow <br>
示例:
> http://127.0.0.1:8080/api/wx/delete/follow?id=1&followid=2
#### 请求参数
需要在httphead添加token
id 为关注者的ID
followid 为被取消关注者的id

### 5.获取某用户关注的所有用户
获取某用户的粉丝
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/get/follow <br>
示例:
> http://127.0.0.1:8080/api/wx/get/follow?id=1
#### 请求参数
需要在httphead添加token
id 用户的id
返回的数据中包含 有hasme （我是否关注了id用户）

## WxAddress 地区接口
### 1.获取武汉所有商场地区
获取武汉所有商场地区
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/address/all <br>
示例:
> http://127.0.0.1:8080/api/wx/address/all
#### 请求参数
需要在httphead添加token
返回 该商场地区的信息， 官方建议游玩时间，费用等 addressid为标签绑定在Article上

### 2.添加武汉商场地区
获取武汉所有商场地区
#### 获取方式
Http POST
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/address/addOne <br>
示例:
> http://127.0.0.1:8080/api/wx/address/addOne
#### 请求参数
需要在httphead添加token <br>
Address address 和1.api中的返回数据一样

## WxArticle 文章接口
### 1.添加一篇文章
添加一篇文章
#### 获取方式
Http POST
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/article/add <br>
示例:
> http://127.0.0.1:8080/api/wx/article/add
#### 请求参数
>ArticleDTO article 文章信息 @RequestParam("img")MultipartFile imgFile 封面图片
```
ArticleDTO
    //文章id 不填
    private int articleId;
    //文章名
    private String title;
    //发表时间
    private String date;
    //封面图片
    private String coverimg;
    //内容
    private String content;
    //点赞数
    private int supportnum;
    //游览时长
    private int playtime;
    //费用
    private int fee;
    //热度
    private long hotnum;
    //用户id
    private int id;
    //地区id
    private String addressId;
```
请求示例在小程序代码中包含

### 2.获得热搜文章
获得热搜文章
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/article/allhot <br>
示例:
> http://127.0.0.1:8080/api/wx/article/allhot?lookUserId=1
#### 请求参数
lookUserId //查看的用户id
#### 分页查询
 @param page 第几页
 @param size 一页大小 默认10条数据
 示例:
> http://127.0.0.1:8080/api/wx/article/hot?page=0&size=10&lookUserId=1
#### 返回数据
```
    "status": 1,
    "data": [
        {
            "article": {
                "articleId": 5,
                "title": "引领潮流的先锋---武商广场",
                "date": "2019-10-25T09:09:58.000+0000",
                "coverimg": "/upload/imgs/wushangSquare.jpg",
                "content": "武商广场购物中心坐落于武汉市具有黄金商圈美誉的解放大道中段，作为武商”摩尔“商业城的主力百货”，秉持高级精致百货的定位和风貌，1996年开业以来连年蝉联”中国单体百货经济效益第一“，是中国最具影响力的高级百货之一。",
                "supportnum": 0,
                "playtime": 129,
                "fee": 109,
                "hotnum": 177
            },
            "issupport": false,
            "avatarUrl": "https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqibiah3Yj5thYy07rMiaibb02x00Vza96Esd7ASQXmQG4nJpcJNLbf59jM3W8EdNtA2l0EC8BaJvZhuw/132",
            "nickName": "C",
            "address": {
                "addressId": "WH004",
                "desc": "",
                "playtime": 96,
                "fee": 112
            }
        }
    ],
    "msg": ""
```

### 3.获得某个用户的所有文章
获得某个用户的所有文章
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/article/user <br>
示例:
> http://127.0.0.1:8080/api/wx/article/user?uid?=1lookUserId=2
#### 请求参数
id //用户
lookUserId //查看的用户id
#### 按热度排序查询
 示例:
> http://127.0.0.1:8080/api/wx/article/userhot?uid?=1lookUserId=2

### 4.获得某个地点的旅游攻略
获得某个地点的旅游攻略
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/article/address <br>
示例:
> http://127.0.0.1:8080/api/wx/article/address?address=WH0001&lookUserId=1
#### 请求参数
address //地区id
lookUserId //查看的用户id
#### 分页查询
 @param page 第几页
 @param size 一页大小 默认10条数据
 示例:
> http://127.0.0.1:8080/api/wx/article/address?address=WH0001&page=0&size=10&lookUserId=1

### 5.点赞某篇文章
点赞某篇文章
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/article/add/support <br>
示例:
> http://127.0.0.1:8080/api/wx/article/add/support?id=1&articleid=1
#### 请求参数
int id 用户id
int articleid 文章id

### 6.删除某篇文章
示例:
> http://127.0.0.1:8080/api/wx/article/delete/support?id=1&articleid=1

### 7.文章热度
文章热度 浏览及热度+1
#### 获取方式
Http GET
#### 数据格式
JSON
#### 请求URL
> app.globalData.urlPath + /api/wx/article/view <br>
示例:
> http://127.0.0.1:8080/api/wx/article/view?articleid=1
#### 请求参数
int articleid 文章id
