(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3d782517"],{"23d7":function(t,e,o){"use strict";o.d(e,"c",(function(){return n})),o.d(e,"o",(function(){return a})),o.d(e,"q",(function(){return s})),o.d(e,"h",(function(){return c})),o.d(e,"j",(function(){return l})),o.d(e,"d",(function(){return i})),o.d(e,"n",(function(){return u})),o.d(e,"k",(function(){return d})),o.d(e,"l",(function(){return h})),o.d(e,"m",(function(){return f})),o.d(e,"f",(function(){return g})),o.d(e,"b",(function(){return m})),o.d(e,"e",(function(){return p})),o.d(e,"p",(function(){return b})),o.d(e,"i",(function(){return y})),o.d(e,"g",(function(){return w})),o.d(e,"a",(function(){return S}));var r=o("b775");function n(t){return Object(r["a"])({url:"/addScoreGoods",method:"post",data:t})}function a(t){return Object(r["a"])({url:"/queryScoreDisable",method:"get",params:t})}function s(t){return Object(r["a"])({url:"/updateScoreDisable",method:"get",params:t})}function c(t){return Object(r["a"])({url:"/queryGoodsBySchoolId",method:"post",data:t})}function l(t){return Object(r["a"])({url:"/queryGoodsTypeBySchoolId",method:"get",params:t})}function i(t){return Object(r["a"])({url:"/deleteGoodsByGoodsId",method:"get",params:t})}function u(t){return Object(r["a"])({url:"/applet/querySchoolByCompanyId",method:"get",params:t})}function d(t){return Object(r["a"])({url:"/queryOrderByOrderNum",method:"get",params:t})}function h(t){return Object(r["a"])({url:"/queryOrderBySchoolId",method:"post",data:t})}function f(t){return Object(r["a"])({url:"/queryOrderYwcAndDshNum",method:"get",params:t})}function g(t){return Object(r["a"])({url:"/getOrderNum",method:"get",params:t})}function m(t){return Object(r["a"])({url:"/addGoodsType",method:"post",data:t})}function p(t){return Object(r["a"])({url:"/deleteGoodsType",method:"get",params:t})}function b(t){return Object(r["a"])({url:"/queryScoreSumBySchoolId",method:"post",data:t})}function y(t){return Object(r["a"])({url:"/queryGoodsDetailByStudentId",method:"get",params:t})}function w(t){return Object(r["a"])({url:"/queryDynamicConditionBySchoolId",method:"get",params:t})}function S(t){return Object(r["a"])({url:"/addDynamicCondition",method:"post",data:t})}},4260:function(t,e,o){"use strict";o.d(e,"a",(function(){return r})),o.d(e,"b",(function(){return n})),o.d(e,"c",(function(){return a}));o("99af"),o("d3b7"),o("25f0");function r(t){var e,o=s(t);if("array"===o)e=[];else{if("object"!==o)return t;e={}}if("array"===o)for(var n=0,a=t.length;n<a;n++)e.push(r(t[n]));else if("object"===o)for(var c in t)e[c]=r(t[c]);return e}function n(t,e){var o=new Date(t),r=o.getFullYear(),n=o.getMonth()+1<10?"0"+(o.getMonth()+1):o.getMonth()+1,a=o.getDate()<10?"0"+o.getDate():o.getDate();o.getHours(),o.getHours(),o.getMinutes(),o.getMinutes(),o.getSeconds(),o.getSeconds();return"".concat(r,"-").concat(n,"-").concat(a)}function a(t,e){var o=new Date(t),r=o.getFullYear(),n=o.getMonth()+1<10?"0"+(o.getMonth()+1):o.getMonth()+1,a=o.getDate()<10?"0"+o.getDate():o.getDate(),s=o.getHours()<10?"0"+o.getHours():o.getHours(),c=o.getMinutes()<10?"0"+o.getMinutes():o.getMinutes(),l=o.getSeconds()<10?"0"+o.getSeconds():o.getSeconds();return"yyyy-MM-dd"==e?"".concat(r,"-").concat(n,"-").concat(a):"".concat(r,"-").concat(n,"-").concat(a," ").concat(s,":").concat(c,":").concat(l)}function s(t){var e=Object.prototype.toString,o={"[object Boolean]":"boolean","[object Number]":"number","[object String]":"string","[object Function]":"function","[object Array]":"array","[object Date]":"date","[object RegExp]":"regExp","[object Undefined]":"undefined","[object Null]":"null","[object Object]":"object"};return t instanceof Element?"element":o[e.call(t)]}},8319:function(t,e,o){"use strict";o("9b93")},"9b93":function(t,e,o){},fe3b:function(t,e,o){"use strict";o.r(e);var r=function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",{staticClass:"content-body"},[o("div",{staticClass:"table-action"},[o("div",{staticClass:"lf"},[o("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入手机号",size:"small"},on:{input:t.goSearch},model:{value:t.searchInfo,callback:function(e){t.searchInfo=e},expression:"searchInfo"}},[o("el-button",{attrs:{slot:"append",icon:"el-icon-search"},on:{click:t.searchOrder},slot:"append"})],1)],1),o("div",{staticClass:"rt"},[o("el-badge",{staticClass:"item",attrs:{value:t.ywc+t.dsh==0?"":t.ywc+t.dsh}},[o("el-button",{style:t.allStyle,attrs:{size:"small"},on:{click:t.findAll}},[t._v("全部")])],1),o("el-badge",{staticClass:"item",attrs:{value:0==t.ywc?"":t.ywc}},[o("el-button",{style:t.finshStyle,attrs:{size:"small"},on:{click:t.findFinish}},[t._v("已完成")])],1),o("el-badge",{staticClass:"item",attrs:{value:0==t.dsh?"":t.dsh}},[o("el-button",{style:t.waitStyle,attrs:{size:"small"},on:{click:t.findWait}},[t._v("待收货")])],1),o("el-button",{attrs:{type:"success",size:"small",icon:"el-icon-upload2"},on:{click:t.exportOrderMsg}},[t._v("导出")])],1)]),o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{border:"",stripe:"","element-loading-text":"拼命加载中","element-loading-background":"rgba(255, 255, 255, 0.8)",data:t.orderArr,"tooltip-effect":"dark",height:t.tableHeight},on:{"row-click":t.handleRowClick,"selection-change":t.handleSelectionChange}},[o("el-table-column",{attrs:{type:"selection",width:"80",fixed:""}}),o("el-table-column",{attrs:{label:"流水号",type:"index",width:"80"}}),o("el-table-column",{attrs:{label:"订单号",sortable:"",prop:"orderNum",width:"120"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("div",{staticClass:"seeGoods",on:{click:function(o){return t.showOrder(e.$index,e.row)}}},[t._v(" "+t._s(e.row.orderNum)+" ")])]}}])}),o("el-table-column",{attrs:{label:"收货人名称",prop:"orderCustomer",width:"120"}}),o("el-table-column",{attrs:{label:"支付积分",sortable:"",prop:"orderScore",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("i",{staticClass:"el-icon-coin"}),t._v(" "+t._s(e.row.orderScore)+" ")]}}])}),o("el-table-column",{attrs:{label:"支付时间",sortable:"",prop:"orderTime",width:"150"}}),o("el-table-column",{attrs:{label:"完成时间",sortable:"",prop:"addressTime",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("div",[t._v(" "+t._s(null==e.row.addressTime?"待完成":e.row.addressTime)+" ")])]}}])}),o("el-table-column",{attrs:{label:"订单状态",sortable:"",prop:"statu",width:"120"}}),o("el-table-column",{attrs:{label:"订单手机号",sortable:"",prop:"orderPhone",width:"200"}}),o("el-table-column",{attrs:{label:"核销员",sortable:"",prop:"teacherName",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("div",[t._v(" "+t._s(null==e.row.teacherName?"待定":e.row.teacherName)+" ")])]}}])}),o("el-table-column",{attrs:{label:"取货校区",prop:"schoolName",width:"120"}})],1),o("div",{staticClass:"table-page__action"},[o("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next, jumper",total:t.allNum,"page-size":t.pageNum},on:{"current-change":t.changePage}})],1),o("div",{staticClass:"showOrderDrawer"},[o("el-drawer",{attrs:{title:"订单详情",visible:t.table,direction:"rtl",size:"50%"},on:{"update:visible":function(e){t.table=e}}},[o("el-table",{attrs:{data:t.orderData.params}},[o("el-table-column",{attrs:{type:"index",label:"编号",width:"150"}}),o("el-table-column",{attrs:{prop:"goodsName",label:"商品名",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.goods.goodsName))]}}])}),o("el-table-column",{attrs:{prop:"name",label:"图片",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[o("el-image",{staticStyle:{width:"100px",height:"100px"},attrs:{src:e.row.goods.goodsImg,fit:t.fit}})]}}])}),o("el-table-column",{attrs:{prop:"shoppingGoodsNum",label:"数量",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v("x"+t._s(e.row.shoppingGoodsNum))]}}])}),o("el-table-column",{attrs:{prop:"name",label:"购买积分",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[t._v(t._s(e.row.goods.goodsScore))]}}])})],1)],1)],1)],1)},n=[],a=(o("96cf"),o("1da1")),s=o("9dac"),c=o("23d7"),l=(o("4260"),{components:{upload:s["a"]},data:function(){return{tableHeight:window.innerHeight-210,tableData:[],multipleSelection:[],valLength:"",searchInfo:"",timeout:null,pageCount:null,pageDataList:[],schoolId:"",loading:!1,adminName:"",orderDetailArr:{},table:!1,orderData:{},storageArr:{companyId:"",schoolId:""},orderArr:[],ywc:"",dsh:"",orderTag:"",finishTag:!1,waitTag:!1,allTag:!0,curPage:"",orPage:"",allNum:0,pageNum:0}},computed:{finshStyle:function(){return 0==this.finishTag?"":"color: #409EFF;border-color: #c6e2ff;background-color: #ecf5ff;"},waitStyle:function(){return 0==this.waitTag?"":"color: #409EFF;border-color: #c6e2ff;background-color: #ecf5ff;"},allStyle:function(){return 0==this.allTag?"":"color: #409EFF;border-color: #c6e2ff;background-color: #ecf5ff;"}},mounted:function(){var t=sessionStorage.getItem("schoolId",t);this.schoolId=t;var e=sessionStorage.getItem("adminName",e);this.adminName=e,this.getStorage(),this.queryOrderBySchoolIdFun(this.storageArr.schoolId,"","",1),this.getOrderNum()},methods:{handleRowClick:function(t,e,o){this.$refs.multipleTable.toggleRowSelection(t)},exportOrderMsg:function(){window.location.href="https://edu.siwonet.com/exportExculScoreOrder?schoolId=this.storageArr.schoolId"},searchOrder:function(){this.queryOrderBySchoolIdFun(this.storageArr.schoolId,this.orderTag,this.searchInfo,1)},changePage:function(t){this.queryOrderBySchoolIdFun(this.storageArr.schoolId,this.orderTag,this.searchInfo,t)},showOrder:function(t,e){var o=this;return Object(a["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,o.orderGoods(e.orderNum);case 2:o.table=!0;case 3:case"end":return t.stop()}}),t)})))()},orderGoods:function(t){var e=this;Object(c["k"])({orderNum:t}).then((function(t){e.orderData=t.data}))},queryOrderBySchoolIdFun:function(t,e,o,r){var n=this;Object(c["l"])({schoolId:t,flag:e,phone:o,pageNo:r}).then((function(t){1==t.code?n.orderArr=[]:(n.orderArr=t.data,n.curPage=t.pages,n.allNum=t.total,n.pageNum=t.pageNum)}))},findAll:function(){this.orderTag="",this.allTag=!0,this.waitTag=!1,this.finishTag=!1,this.searchInfo="",this.queryOrderBySchoolIdFun(this.storageArr.schoolId,"","",1)},findFinish:function(){this.orderTag=0,this.finishTag=!0,this.waitTag=!1,this.allTag=!1,this.queryOrderBySchoolIdFun(this.storageArr.schoolId,this.orderTag,this.searchInfo,1)},findWait:function(){this.orderTag=1,this.waitTag=!0,this.finishTag=!1,this.allTag=!1,this.queryOrderBySchoolIdFun(this.storageArr.schoolId,this.orderTag,this.searchInfo,1)},sys_typeOrder:function(t){var e=this;this.orderArr=[],Object(c["m"])({schoolId:this.storageArr.schoolId,flag:t}).then((function(t){e.orderArr=t.data}))},getOrderNum:function(){var t=this;Object(c["f"])({schoolId:this.storageArr.schoolId}).then((function(e){0==e.code&&(t.ywc=e.ywc,t.dsh=e.dsh)}))},getStorage:function(){this.storageArr.companyId=window.sessionStorage.getItem("companyId"),this.storageArr.schoolId=window.sessionStorage.getItem("schoolId")},handleSelectionChange:function(t){this.multipleSelection=t,this.valLength=t.length},goSearch:function(){var t=this;this.searchInfo;clearTimeout(this.timeout),this.timeout=setTimeout((function(){t.getChangePage({className:t.searchInfo,page:1,limit:20})}),300)}}}),i=l,u=(o("8319"),o("2877")),d=Object(u["a"])(i,r,n,!1,null,"7b928e60",null);e["default"]=d.exports}}]);