(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-e4315ca4"],{"23c5":function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"content-body"},[n("div",{staticClass:"table-action"},[n("div",{staticClass:"lf"}),n("div",{staticClass:"rt"},[n("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-plus"},on:{click:t.addclick}},[t._v("新增")]),n("el-button",{attrs:{size:"small",type:"info",icon:"el-icon-edit"},on:{click:function(e){return t.handleEdit(t.valLength,t.multipleSelection)}}},[t._v("编辑")]),n("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:function(e){return t.deleteClick(t.valLength,t.multipleSelection)}}},[t._v("删除")])],1)]),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{border:"",stripe:"","element-loading-text":"拼命加载中","element-loading-background":"rgba(255, 255, 255, 0.8)",data:t.pageDataList,"tooltip-effect":"dark",height:t.tableHeight},on:{"row-click":t.handleRowClick,"selection-change":t.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"80",fixed:""}}),n("el-table-column",{attrs:{label:"编号",type:"index",width:"80"}}),n("el-table-column",{attrs:{label:"图片",prop:"slideshowImg",width:"150"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("div",[n("el-image",{staticStyle:{width:"50px",height:"30px"},attrs:{src:e.row.slideshowImg,fit:"scale-down","preview-src-list":t.slideshowImgAll}})],1)]}}])}),n("el-table-column",{attrs:{label:"创建人",sortable:"",prop:"slideshowCreator",width:"150"}}),n("el-table-column",{attrs:{label:"创建时间",sortable:"",prop:"slideshowCreationTime",width:"200"}}),n("el-table-column",{attrs:{label:"编辑人",sortable:"",prop:"slideshowEditor",width:"150"}}),n("el-table-column",{attrs:{label:"最后一次编辑的时间",sortable:"",prop:"slideshowEditSession",width:"200"}})],1),n("div",{staticClass:"table-page__action"},[n("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next, jumper",total:t.pageCount,"page-size":10},on:{"current-change":t.changePage}})],1),n("el-dialog",{attrs:{title:"新增轮播图片",width:"600px","close-on-click-modal":!1,visible:t.isaddmaskshow},on:{"update:visible":function(e){t.isaddmaskshow=e}}},[t.isaddmaskshow?n("TheForm",{ref:"newForm"}):t._e(),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{staticClass:"noadd",on:{click:t.noaddclick}},[t._v("取消")]),n("el-button",{staticClass:"okadd",attrs:{type:"primary"},on:{click:t.okaddclick}},[t._v("确认")])],1)],1),n("el-dialog",{attrs:{title:"编辑轮播图片",width:"600px","close-on-click-modal":!1,visible:t.iseditmaskshow},on:{"update:visible":function(e){t.iseditmaskshow=e}}},[t.iseditmaskshow?n("TheForm",{ref:"editForm",attrs:{type:"edit"}}):t._e(),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{staticClass:"noedit",on:{click:t.noEditclick}},[t._v("取消")]),n("el-button",{staticClass:"okedit",attrs:{type:"primary"},on:{click:t.okEditclick}},[t._v("确认")])],1)],1)],1)},a=[],i=(n("a15b"),n("d3b7"),n("5530")),s=(n("96cf"),n("1da1")),r=n("9dac"),c=n("caa4"),l=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("el-form",{ref:"form",attrs:{"label-width":"100px",model:t.form,rules:t.rules}},[n("el-form-item",{ref:"uploadForm",staticClass:"upload-img-form",attrs:{prop:"slideshowImg",label:"图片"}},[n("upload",{attrs:{params:{companyId:t.companyId}},model:{value:t.form.slideshowImg,callback:function(e){t.$set(t.form,"slideshowImg",e)},expression:"form.slideshowImg"}}),n("div",{},[t._v("(建议上传文件大小不超过200KB，图片宽:高——2:1)")])],1)],1)},d=[],u=n("4260"),h={props:{type:{type:String,default:"new"}},components:{upload:r["a"]},data:function(){return{rules:{slideshowImg:[{required:!0,message:"请上传图片",trigger:"blur"}]},form:{slideshowImg:""},defaultForm:{},companyId:""}},watch:{},created:function(){this.init();var t=sessionStorage.getItem("companyId",t);this.companyId=t},mounted:function(){},methods:{isPass:function(){var t=this;return new Promise((function(e){var n=t.$refs.form;n.validate((function(t){e(t)}))}))},init:function(){this.defaultForm=Object(u["a"])(this.form)},getValue:function(){var t=Object(u["a"])(this.form);return t},setValue:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.form=Object(u["a"])(t)},reset:function(){this.form=Object(u["a"])(this.defaultForm),this.$refs.form.resetFields()}},computed:{},filters:{}},m=h,f=n("2877"),g=Object(f["a"])(m,l,d,!1,null,"1da1d968",null),p=g.exports,b={components:{upload:r["a"],TheForm:p},data:function(){return{tableHeight:window.innerHeight-210,tableData:[],isaddmaskshow:!1,iseditmaskshow:!1,editContent:"",filePath:"",multipleSelection:[],valLength:"",selectUploadFileBtn:!0,pageCount:null,pageDataList:[],searchInfo:"",slideshowImgAll:[],schoolId:"",companyId:"",adminName:"",loading:!1}},methods:{handleSelectionChange:function(t){this.multipleSelection=t,this.valLength=t.length},handleRowClick:function(t,e,n){this.$refs.multipleTable.toggleRowSelection(t)},addConsult:function(){var t=this;return Object(s["a"])(regeneratorRuntime.mark((function e(){var n,o;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,t.$refs.newForm.isPass();case 2:if(n=e.sent,0!=n){e.next=5;break}return e.abrupt("return");case 5:o=t.$refs.newForm.getValue(),t.loading=!0,Object(c["c"])(Object(i["a"])(Object(i["a"])({},o),{},{slideshowCreator:t.adminName,schoolId:t.schoolId})).then((function(e){1==e.code?t.$message.error(e.msg):(t.isaddmaskshow=!1,t.$message.success(e.msg),t.getChangePage({page:1,limit:10}))})).finally((function(){t.loading=!1}));case 8:case"end":return e.stop()}}),e)})))()},addclick:function(){var t=this;this.isaddmaskshow=!0,this.$nextTick((function(){t.$refs.newForm.reset()}))},noaddclick:function(){this.isaddmaskshow=!1},okaddclick:function(){this.addConsult()},handleEdit:function(t,e){var n=this;if(t){if(1==t)this.iseditmaskshow=!0,this.editContent=Object(u["a"])(e[0]),this.$nextTick((function(){n.$refs.editForm.reset(),n.$refs.editForm.setValue(n.editContent)}));else if(t>1)return void this.$message.error("您只能选择一个要编辑的内容")}else this.$message.error("您还未选择要编辑的内容")},EditInfo:function(){var t=this;this.loading=!0;var e=this.$refs.editForm.getValue();Object(c["m"])(Object(i["a"])(Object(i["a"])({},e),{},{slideshowEditor:this.adminName,schoolId:this.schoolId})).then((function(e){t.iseditmaskshow=!1,1==e.code?t.$message.error(e.msg):(t.$message.success(e.msg),t.getChangePage({page:1,limit:10}))})).finally((function(){t.loading=!1}))},okEditclick:function(){var t=this;return Object(s["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,t.$refs.editForm.isPass();case 2:if(n=e.sent,0!=n){e.next=5;break}return e.abrupt("return");case 5:t.iseditmaskshow=!1,t.EditInfo();case 7:case"end":return e.stop()}}),e)})))()},noEditclick:function(){this.iseditmaskshow=!1},deleteClick:function(t,e){var n=this;if(t){for(var o=[],a=0;a<e.length;a++){var i=e[a].schoolSlideshowId;o.push(i)}this.$confirm("确认要删除吗？").then((function(){n.loading=!0,Object(c["f"])({schoolSlideshowIds:o.join(",")}).then((function(t){1==t.code?n.$message.error(t.msg):(n.$message.success(t.msg),n.getChangePage({page:1,limit:10}))})).finally((function(){n.loading=!1}))}))}else this.$message.error("您还未选择要删除的内容")},getChangePage:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,Object(c["i"])(Object(i["a"])(Object(i["a"])({},e),{},{schoolId:this.schoolId})).then((function(e){t.pageCount=e.pageCount;var n=e.data;t.pageDataList=e.data;for(var o=[],a=0;a<n.length;a++){var i=n[a].slideshowImg;o.push(i)}t.slideshowImgAll=o})).finally((function(){t.loading=!1}))},changePage:function(t){this.getChangePage({page:t,limit:10})},searchStudentInfo:function(){this.getChangePage({studentName:this.searchInfo})}},mounted:function(){var t=sessionStorage.getItem("schoolId",t);this.schoolId=t;var e=sessionStorage.getItem("adminName",e);this.adminName=e;var n=sessionStorage.getItem("companyId",n);this.companyId=n,this.getChangePage({page:1,limit:10})}},w=b,v=(n("8a79"),n("9425"),Object(f["a"])(w,o,a,!1,null,"1cbb272d",null));e["default"]=v.exports},"2d00e":function(t,e,n){},4260:function(t,e,n){"use strict";n.d(e,"a",(function(){return o})),n.d(e,"b",(function(){return a})),n.d(e,"c",(function(){return i}));n("99af"),n("d3b7"),n("25f0");function o(t){var e,n=s(t);if("array"===n)e=[];else{if("object"!==n)return t;e={}}if("array"===n)for(var a=0,i=t.length;a<i;a++)e.push(o(t[a]));else if("object"===n)for(var r in t)e[r]=o(t[r]);return e}function a(t,e){var n=new Date(t),o=n.getFullYear(),a=n.getMonth()+1<10?"0"+(n.getMonth()+1):n.getMonth()+1,i=n.getDate()<10?"0"+n.getDate():n.getDate();n.getHours(),n.getHours(),n.getMinutes(),n.getMinutes(),n.getSeconds(),n.getSeconds();return"".concat(o,"-").concat(a,"-").concat(i)}function i(t,e){var n=new Date(t),o=n.getFullYear(),a=n.getMonth()+1<10?"0"+(n.getMonth()+1):n.getMonth()+1,i=n.getDate()<10?"0"+n.getDate():n.getDate(),s=n.getHours()<10?"0"+n.getHours():n.getHours(),r=n.getMinutes()<10?"0"+n.getMinutes():n.getMinutes(),c=n.getSeconds()<10?"0"+n.getSeconds():n.getSeconds();return"yyyy-MM-dd"==e?"".concat(o,"-").concat(a,"-").concat(i):"".concat(o,"-").concat(a,"-").concat(i," ").concat(s,":").concat(r,":").concat(c)}function s(t){var e=Object.prototype.toString,n={"[object Boolean]":"boolean","[object Number]":"number","[object String]":"string","[object Function]":"function","[object Array]":"array","[object Date]":"date","[object RegExp]":"regExp","[object Undefined]":"undefined","[object Null]":"null","[object Object]":"object"};return t instanceof Element?"element":n[e.call(t)]}},"8a79":function(t,e,n){"use strict";n("2d00e")},9425:function(t,e,n){"use strict";n("e31a")},caa4:function(t,e,n){"use strict";n.d(e,"a",(function(){return a})),n.d(e,"k",(function(){return i})),n.d(e,"d",(function(){return s})),n.d(e,"g",(function(){return r})),n.d(e,"j",(function(){return c})),n.d(e,"c",(function(){return l})),n.d(e,"m",(function(){return d})),n.d(e,"f",(function(){return u})),n.d(e,"i",(function(){return h})),n.d(e,"b",(function(){return m})),n.d(e,"l",(function(){return f})),n.d(e,"e",(function(){return g})),n.d(e,"h",(function(){return p}));var o=n("b775");function a(t){return Object(o["a"])({url:"/addSchool",method:"post",data:t})}function i(t){return Object(o["a"])({url:"/updateSchool",method:"post",data:t})}function s(t){return Object(o["a"])({url:"/deleteManySchool",method:"get",params:t})}function r(t){return Object(o["a"])({url:"/limitSchool",method:"get",params:t})}function c(t){return Object(o["a"])({url:"/queryParamBySchoolId",method:"get",params:t})}function l(t){return Object(o["a"])({url:"/addSchoolSlideshow",method:"post",data:t})}function d(t){return Object(o["a"])({url:"/updateSchoolSlideshow",method:"post",data:t})}function u(t){return Object(o["a"])({url:"/deleteSchoolSlideshowIds",method:"get",params:t})}function h(t){return Object(o["a"])({url:"/limitSchoolSlideshow",method:"get",params:t})}function m(t){return Object(o["a"])({url:"/addSchoolHonor",method:"post",data:t})}function f(t){return Object(o["a"])({url:"/updateSchoolHonor",method:"post",data:t})}function g(t){return Object(o["a"])({url:"/deleteSchoolHonorIds",method:"get",params:t})}function p(t){return Object(o["a"])({url:"/limitSchoolHonor",method:"get",params:t})}},e31a:function(t,e,n){}}]);