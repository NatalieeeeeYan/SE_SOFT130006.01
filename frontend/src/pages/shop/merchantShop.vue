<template>
  <q-layout view="hHh lpR fFf">

    <q-header elevated class="bg-primary text-white">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />

        <q-toolbar-title>

          <q-avatar>
            <img src="https://cdn.quasar.dev/logo-v2/svg/logo-mono-white.svg">
          </q-avatar>
          {{ shopName }}
        </q-toolbar-title>
        <q-btn flat round dense icon="update" class="q-mr-xs" @click="updata"></q-btn>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
      <div class="q-pa-md row items-start q-gutter-md">
        <!--商店基本信息-->
        <div class="q-pa-ma" style="width:400px;">
          <div style="width:300px;">
            <q-img :src="url" style="border: 50%; left:50px" />
            <div class="text-h4 q-mt-sm q-mb-xs" style="margin-left:100px;height:50px"> {{ shopName }}</div>
            <div>
              <q-separator style="margin-left:80px" />
            </div>
            <div class="text-caption text-grey" style="margin-left:90px; max-height:50px">
              {{ shopIntroduce }}
            </div>
            <div class="col-auto text-grey text-h6 q-pt-md row no-wrap items-center"
              style="margin-left:80px; height:50px">
              <q-icon name="place" />
              {{ shopAddress }}
            </div>
            <div class="text-grey text-h6" style="margin-left:80px; max-height:50px">
              注册时间 {{ shopRegistrationTime }}
            </div>
          </div>
        </div>

        <!--商店商品展示-->
        <div class="q-pa-ma">
          <div>
            <q-tabs v-model="tab" dense class="text-grey" active-color="primary" indicator-color="primary" align="justify"
              narrow-indicator>
              <q-tab name="Commodity" label="Commodity" />
              <!-- <q-tab name="changeMessage" label="changeMessage" /> -->
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="Commodity">
                <div class="flex justify-center">
                  <q-scroll-area :visible="visible" style="height: 620px; width: 900px;" class="flex justify-center">
                    <div class="flex q-justify-around" style="width: 900px;">
                      <div v-for="commodity in commodities" :key="commodity" class="flex q-py-xs justify-around"
                        style="width: 450px;">
                        <q-card class="my-card">
                          <div class="q-pa-md">
                            <q-carousel class="commodity_img" swipeable
                              animated v-model="commodity.slide" height="200px" thumbnails infinite>
                              <q-carousel-slide v-for="(image, index) in commodity.image" :key="index" :name="index + 1" :img-src="image" />
                            </q-carousel>
                          </div>

                          <q-card-section>
                            <!-- <q-btn fab color="primary" icon="delete" class="absolute"
                              style="top: 0; right: 12px; transform: translateY(-50%);"
                              @click="deleteCommodity(commodity)" /> -->

                            <div class="row no-wrap items-center">
                              <div class="col text-h6 ellipsis">
                                <q-btn flat class="text-h6" @click="toEditCommodity(commodity)">
                                  {{ commodity.goodsName }}
                                </q-btn>
                              </div>
                              <!-- <div class="col-auto text-grey text-caption q-pt-md row no-wrap items-center">
                                下架商品
                              </div> -->
                            </div>

                            <q-separator />
                            <!-- <q-rating v-model="stars" :max="5" size="32px" /> -->
                            <div class="text-subtitle3" style="margin-top: 3%;">
                              状态: {{ commodity.status }}
                            </div>
                            <div class="text-subtitle1">
                              ¥ price: {{ commodity.price }}
                            </div>
                            <div class="text-caption text-grey">
                              {{ commodity.description }}
                            </div>
                          </q-card-section>
                        </q-card>
                        <q-separator />

                      </div>
                    </div>
                  </q-scroll-area>
                </div>
              </q-tab-panel>

              <!--新增tab在这里写-->
            </q-tab-panels>
          </div>

        </div>

        <div>
          <q-dialog v-model="layout_delete" no-click-outside-close>
            <q-layout view="Lhh lpR fff" container class="bg-white">
              <q-header class="bg-primary">
                <q-toolbar>
                  <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
                  <q-toolbar-title>下架商品</q-toolbar-title>
                  <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
                  <q-btn flat v-close-popup round dense icon="close" @click="layout_delete = false" />
                </q-toolbar>
              </q-header>

              <q-footer class="bg-black text-white">
                <q-toolbar inset>
                  <q-toolbar-title>Apply</q-toolbar-title>
                </q-toolbar>
              </q-footer>

              <q-page-container>
                <q-page padding style="height:100px">
                  <div class="text-h5 q-mt-sm q-mb-xs" style="height:100px"> <q-chip size="lg" icon="bookmark">
                      商品名称
                    </q-chip> {{ delete_goodsName }}</div>
                  <div class="text-h5 q-mt-sm q-mb-xs" style="height:100px"> <q-chip size="lg" icon="bookmark">
                      商品简介
                    </q-chip> {{ delete_description }}</div>
                  <div class="text-h5 q-mt-sm q-mb-xs" style="height:120px"> <q-chip size="lg" icon="bookmark">
                      商品价格
                    </q-chip> ¥ {{ delete_price }}</div>
                  <q-btn color="primary" glossy label="Delete" @click="deleteOneCommodity" />
                </q-page>
              </q-page-container>
            </q-layout>
          </q-dialog>
        </div>

        <!--上架商品-->
        <div>
          <q-btn round color="purple" icon="add" class="absolute" @click="layout = true"
            style="top: 600px; right: 50px; transform: translateY(-50%);" size="30px">
          </q-btn>
          <q-dialog v-model="layout" no-click-outside-close>
            <q-layout view="Lhh lpR fff" container class="bg-white">
              <q-header class="bg-primary">
                <q-toolbar>
                  <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
                  <q-toolbar-title>申请上架商品</q-toolbar-title>
                  <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
                  <q-btn flat v-close-popup round dense icon="close" @click="layout = false" />
                </q-toolbar>
              </q-header>

              <q-footer class="bg-black text-white">
                <q-toolbar inset>
                  <q-toolbar-title>Apply</q-toolbar-title>
                </q-toolbar>
              </q-footer>

              <q-page-container>
                <q-page padding>
                  <q-form ref="myForm" @submit="onSubmit" @reset="onReset" class="q-gutter-md">
                    <br>
                    <q-input filled v-model="goodsName" label="商品名称 *" hint="Name and surname" lazy-rules
                      :rules="[val => val && val.length > 0 && val.length < 13 || '请输入您的商品名称']" />

                    <q-input filled v-model="description" label="商品简介 *" hint="Introduce your store within 128 characters"
                      lazy-rules :rules="[val => val && val.length > 0 || '请输入您的简介',
                      val => val.length < 129 || '超过字数限制']">
                    </q-input>

                    <q-input filled v-model="price" label="商品价格 *" hint="registered funds" lazy-rules :rules="[val => val && val.length > 0 || '请输入商品价格',
                    checkprice || '价格需为大与0的浮点数']"></q-input>

                    <q-uploader field-name="files" style="width: 530px" url="http://localhost:9999/Goods/uploadImg"
                      label="Restricted to images" multiple @uploading="onFileUploading" accept="image/*"
                      Content-Type='multipart/form-data' :auto-upload="false" @uploaded="handleUpload($event)"
                      @rejected="onRejected" />

                    <q-toggle v-model="accept" label="我同意隐私条款" />

                    <div class="submit">
                      <q-btn label="Submit" type="submit" color="red-4" />
                      <q-btn label="Reset" type="reset" color="red-4" flat class="q-ml-sm" />
                    </div>

                  </q-form>
                </q-page>
              </q-page-container>
            </q-layout>
          </q-dialog>
        </div>

      </div>
    </q-page-container>

    <!-- 修改商品信息 -->
    <div class="editCommodity" v-if="editCommodity">
      <q-dialog v-model="editCommodity" width>
        <q-layout view="Lhh lpR fff" container class="bg-white">

          <q-header class="bg-primary">
            <q-toolbar>
              <q-btn flat @click="drawer = !drawer" round dense icon="menu" />
              <q-toolbar-title>修改商品信息</q-toolbar-title>
              <q-btn flat @click="drawerR = !drawerR" round dense icon="menu" />
              <q-btn flat v-close-popup round dense icon="close" />
            </q-toolbar>
          </q-header>

          <q-drawer bordered v-model="drawer" :width="200" :breakpoint="600" class="bg-grey-3 q-pa-sm">
            <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
          </q-drawer>

          <q-drawer side="right" bordered v-model="drawerR" :width="200" :breakpoint="300" class="bg-grey-3 q-pa-sm">
            <div v-for="n in 50" :key="n">Drawer {{ n }} / 50</div>
          </q-drawer>

          <q-page-container>
            <q-page padding>
              <q-form ref="myForm" action="http://localhost:9999/user" method="post" @submit="postProfileUpdate"
                @reset="onReset" class="q-gutter-md">
                <dl>
                  <dt>
                    <label class="label" for="userName">商品名称</label>
                  </dt>
                  <dd>
                    <q-input filled name="goodsName" v-model="editComName" @click="code = 0" :disable="viewOnly" />
                  </dd>
                </dl>

                <dl>
                  <dt>
                    <label class="label" for="userPhone">价格</label>
                  </dt>
                  <dd>
                    <q-input filled name="price" v-model="editComPrice" @click="code = 0" :disable="viewOnly" />
                  </dd>
                </dl>

                <dl>
                  <dt>商品描述</dt>
                  <dd>
                    <q-input filled name="description" v-model="editComDescription" :disable="viewOnly" />
                  </dd>
                </dl>

                <div class="button-box">
                  <div v-if="viewOnly" class="edit-button">
                    <q-btn color="secondary" label="修改信息" @click="viewEditModeChange" />
                  </div>

                  <div v-if="!viewOnly" class="submit-button">
                    <q-btn color="secondary" label="提交修改" @click="postCommodityEdit" />
                  </div>
                  <q-tooltip v-if="editNotification" anchor="bottom middle" self="top middle" :offset="[10, 10]">
                    <strong>{{ editMsg }}</strong>
                    (<q-icon name="keyboard_arrow_down" />)
                  </q-tooltip>
                </div>
              </q-form>
            </q-page>
          </q-page-container>
        </q-layout>
      </q-dialog>
    </div>

  </q-layout>
</template>

<script setup>
import { ref, computed, getCurrentInstance } from 'vue'
import { useStore } from 'src/store'
import axios from 'axios'
import { onMounted } from 'vue'

const store = useStore()
const shopId = 1;
const axiosInstance = axios.create({
  baseURL: 'http://localhost:9999',
});

const tab = ref("Commodity")
const url = ref('https://avatars.githubusercontent.com/u/105032850?s=400&u=285d7d130058e413bb8797cb52bc10f75c343076&v=4')
const accept = ref(false)
const layout = ref(false)
const layout_delete = ref(false)
let editCommodity = ref(false)
let viewOnly = ref(true)

const commodities = ref([])
const instance = getCurrentInstance()

//上架商品
const goodsName = ref(null)
const description = ref(null)
const price = ref(null)
const image = ref([])

//下架商品信息
const delete_goodsName = ref(null)
const delete_description = ref(null)
const delete_price = ref(null)
const delete_id = ref(null)

//修改申请记录
let editComId = ref(null)
let editComName = ref(null)
let editComDescription = ref(null)
let editComPrice = ref(null)
let editComShopid = ref(null)
let editComImage = ref(null)
let editComStatus = ref(null)
const shops = ref([])
const code = ref(null)

const shopName = computed(() => {
  return shops.value ? shops.value.shopName : '';
});

const shopIntroduce = computed(() => {
  return shops.value ? shops.value.introduce : '';
});

const shopAddress = computed(() => {
  return shops.value ? shops.value.address : '';
});

const shopRegistrationTime = computed(() => {
  return shops.value ? shops.value.registrationTime : '';
});


const myForm = ref(null);

function checkprice(price) {
  if (isNaN(price)) {
    return false;
  }
  if (price < 0) {
    return false;
  }
  return true; // 输入值合法
}

function deleteCommodity(commodity) {
  console.log('delete commodity parameter: ', commodity.id)
  axiosInstance.put('/Goods/remove',
    {
      id: commodity.id,
    },
    {
      params: {
        goodsId: commodity.id,
      }
    }).then((response) => {
      console.log('下架商品：', response)
    });
}

//下架商品
function deleteOneCommodity() {
  axiosInstance.put('/Goods/remove',
    {
      goodsId: delete_id.value
    },
    {
      params: {
        goodsId: delete_id.value
      }
    }).then((response) => {
      console.log("下架商品")
      const r = response.data['data']
      console.log(r)
      layout_delete.value = false

      update()
    });
}

function update() {
  //显示该店铺的信息
  axiosInstance.get('/shop/showShopByShopId', {
    params: {
      shopId: 1
    }
  }).then((response) => {
    const r = response.data['data']
    const [year, month, day] = r.registrationTime;
    r.registrationTime = `${year}-${month}-${day}`;
    console.log('shop message')
    shops.value = r
    console.log(shops.value)
  });

  //显示该店铺的所有上架成功的商品
  axiosInstance.get('/Goods/showAddRecord_1', {
    params: {
      shopId: 1
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('showAddRecord_1')
    commodities.value = r
    console.log(commodities.value)
  });

  instance.proxy.$forceUpdate();
}

// 提交上架商品的表单
function onSubmit() {
  axiosInstance
    .post('/Goods/addGoodsApply',
      {
        goodsName: goodsName.value,
        description: description.value,
        price: price.value,
        shopId: 1,
        image: image.value
      },
      {
        params: {
          shopId: 1
        }
      }
    )
    .then((response) => {
      //console.log('Response data:', response.data)
      // 处理返回数据
      // 导航到另一个页面
      console.log("shangjia")
      code.value = response.data['code'];
      if (code.value == 20000) {
        layout.value = false;
        console.log('layout');
        console.log(layout.value);
        onReset()
      }
      console.log(response.data);
    })
    .catch((error) => {
      console.error('Error:', error);
      // 处理错误
      myForm.value.resetValidation();
    });
}

function onReset() {
  goodsName.value = null
  description.value = null
  price.value = null
  image.value = []
  myForm.value.resetValidation();
}

function handleUpload(event) {
  console.log("handleUpload")
  console.log(event)
  const r = event.xhr.responseText;
  const r_obj = JSON.parse(r)
  console.log(r_obj)
  r_obj.message = 'http://localhost:9999/img/' + r_obj.message
  image.value.push(r_obj.message)
  console.log(image.value)
}


onMounted(() => {
  //显示该店铺的信息
  axiosInstance.get('/shop/showAddRecord_1', {
    params: {
      shopId: shopId
    }
  }).then((response) => {
    const r = response.data['data']
    const [year, month, day] = r.registrationTime;
    r.registrationTime = `${year}-${month}-${day}`;
    console.log('shop message')
    shops.value = r
    console.log(shops.value)
  });

  //显示该店铺的所有商品
  axiosInstance.get('/Goods/showShopAllGoods', {
    params: {
      shopId: shopId,
    }
  }).then((response) => {
    const r = response.data['data']
    console.log('show all comodities of shop ', shopId, ': ', r)
    r.map(obj => {
      switch (obj.status) {
        case 0:
          obj.status = '上架审核中';
          break;
        case 1:
          obj.status = '上架成功';
          break;
        case 2:
          obj.status = '上架失败';
          break;
        case 5:
          obj.status = '已移除';
          break;
        case 7:
          obj.status = '商品详情更新审核中';
          break;
        case 8:
          obj.status = '商品详情更新成功';
          break;
        case 9:
          obj.status = '商品详情更新失败';
          break;
        default:
          break;
      }
      return obj;
    });
    commodities.value.splice(0, commodities.value.length, ...r);
    console.log(commodities.value)
  });

});

function toEditCommodity(commodity) {
  console.log('edit commodity msg: ', commodity);
  editCommodity.value = true
  editComName.value = commodity.goodsName
  editComId.value = commodity.id
  editComDescription.value = commodity.description
  editComPrice.value = commodity.price
  editComShopid.value = commodity.shopId
  editComImage.value = commodity.image
  editComStatus.value = commodity.status
}

function viewEditModeChange() {
  viewOnly.value = !viewOnly.value
  console.log('view mode: ', viewOnly.value)
}

function postCommodityEdit() {
  console.log("post edit commodity: \ngoods id: ", editComId.value,
    "\ngoods name: ", editComName.value,
    "\ngoods price: ", editComPrice.value,
    "\ngoods description: ", editComDescription.value,
    "\ngoods shop id: ", editComShopid.value,
    "\ngoods image: ", editComImage.value,)

    axiosInstance
    .post('/Goods/updateGoodsApply',
      {
        goodsName: editComName.value,
        description: editComDescription.value,
        price: editComPrice.value,
        shopId: editComShopid.value,
        image: editComImage.value
      },
      {
        params: {
          goodsId: editComId.value,
        }
      }
    )
    .then((response) => {
      //console.log('Response data:', response.data)
      // 处理返回数据
      // 导航到另一个页面
      console.log("shangjia")
      code.value = response.data['code'];
      if (code.value == 20000) {
        layout.value = false;
        console.log('layout');
        console.log(layout.value);
      }
      console.log(response.data);
    })
    .catch((error) => {
      console.error('Error:', error);
      // 处理错误
      // myForm.value.resetValidation();
    });
}
</script>

<style lang="sass" scoped>
.my-card
  width: 100%
  max-width: 350px
  height: 100%
  max-height: 430px
</style>
