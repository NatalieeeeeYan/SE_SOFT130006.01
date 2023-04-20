# lab3 more

注意修改application.yml中的配置:
  * 图片存储地址
  * mysql密码

ps： 不要使用奇怪的库。。
经测试发现周三上午显示的缺少模块来自于以下代码
这个库在代码中并没有使用，但是导入了导致了错误
```javascript
import { response } from 'express';
import { responseInterceptor } from 'http-proxy-middleware';
```

已发现的一些奇怪显示:
  * admin中显示已经闭店的店，在用户首页仍然显示（仍需测试，可能因为我直接改了数据库的原因）

admin:
  已完成：
   * 所有图片连接
   * 所有卡片排列为两个一排
   * 开店审批：同意&拒绝
   * 闭店审批：同意&拒绝
  待完成
   * 所有商品审批都无法使用（商品审批等待后端回复）
   * 注意各个页面的刷新代码
  
merchantShop:
   已完成:
   * 已经解决id无法传入的问题
   * 所有图片连接
   * 该页面刷新
   * 下架商品自动刷新
   * 页面排版按比例调整
   * 店铺信息连接
   * 店铺流水显示

   待完成:
   * 下架的第一个商品会在`已下架商品`中显示两次(目测后端问题，没有细测)
   * 修改商品信息未测（数据库问题，等待后端答复）
   * 商品图片链接

merchantPage:
   已完成:
   * 店铺对应跳转
   * 首页按比例调整
   * 申请闭店弹窗提示
  
   待完成:
   * 在all中多次点击下架店铺，APPLYING中会反复出现
   * 此处显示逻辑有多处不对，建议仔细测试
   * 不知道merchatShop中那些TODO帮助页面，联系我们页面是准备在哪里显示。。？
   * 商品图片链接

UserPage:
   已完成:
   * 商品图片链接
   * 已批准店铺的显示
   * 单个店铺的跳转
   * 页面排版按比例调整
   * 新增可以打分的星星
   * 加入购物车

   待完成：（有时间）
   * 加入购物车时增加消息提示

shopPage,merchantShop:
   已完成:
   * 图片，店铺信息连接
  
   未完成:
   * 店铺信息排版（有时间改）

ShoppingCart:
   已完成：
   * 购物车加入逻辑
   * 增加减少商品数量
   * 批量删除
   
   有问题:
   * 购物车商品不返回图片
   * 购物车商品数量为0的时候没有删除
