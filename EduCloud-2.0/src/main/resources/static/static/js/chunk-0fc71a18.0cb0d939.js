(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0fc71a18"],{"35ef":function(t,e,n){},4124:function(t,e,n){"use strict";n.d(e,"c",(function(){return a})),n.d(e,"r",(function(){return i})),n.d(e,"h",(function(){return r})),n.d(e,"m",(function(){return s})),n.d(e,"e",(function(){return l})),n.d(e,"t",(function(){return c})),n.d(e,"j",(function(){return u})),n.d(e,"o",(function(){return d})),n.d(e,"a",(function(){return m})),n.d(e,"p",(function(){return f})),n.d(e,"f",(function(){return h})),n.d(e,"k",(function(){return g})),n.d(e,"d",(function(){return p})),n.d(e,"s",(function(){return b})),n.d(e,"i",(function(){return v})),n.d(e,"n",(function(){return k})),n.d(e,"b",(function(){return w})),n.d(e,"q",(function(){return _})),n.d(e,"g",(function(){return C})),n.d(e,"l",(function(){return j}));var o=n("b775");function a(t){return Object(o["a"])({url:"/addColorImg",method:"post",data:t})}function i(t){return Object(o["a"])({url:"/updateColorImg",method:"post",data:t})}function r(t){return Object(o["a"])({url:"/deleteColorImg",method:"get",params:t})}function s(t){return Object(o["a"])({url:"/limitColorImg",method:"get",params:t})}function l(t){return Object(o["a"])({url:"/addPoster",method:"post",data:t})}function c(t){return Object(o["a"])({url:"/updatePoster",method:"post",data:t})}function u(t){return Object(o["a"])({url:"/deletePoster",method:"get",params:t})}function d(t){return Object(o["a"])({url:"/limitPosters",method:"get",params:t})}function m(t){return Object(o["a"])({url:"/addActivity",method:"post",data:t})}function f(t){return Object(o["a"])({url:"/updateActivity",method:"post",data:t})}function h(t){return Object(o["a"])({url:"/deleteActivity",method:"get",params:t})}function g(t){return Object(o["a"])({url:"/limitActivitie",method:"get",params:t})}function p(t){return Object(o["a"])({url:"/addConsult",method:"post",data:t})}function b(t){return Object(o["a"])({url:"/updateConsult",method:"post",data:t})}function v(t){return Object(o["a"])({url:"/deleteConsult",method:"get",params:t})}function k(t){return Object(o["a"])({url:"/limitConsults",method:"get",params:t})}function w(t){return Object(o["a"])({url:"/addBulletinBoard",method:"post",data:t})}function _(t){return Object(o["a"])({url:"/updateBulletinBoard",method:"post",data:t})}function C(t){return Object(o["a"])({url:"/deleteBulletinBoard",method:"get",params:t})}function j(t){return Object(o["a"])({url:"/limitBulletinBoard",method:"get",params:t})}},"64e4":function(t,e,n){"use strict";n("35ef")},8099:function(t,e,n){},b8d0:function(t,e,n){"use strict";n("8099")},fd77:function(t,e,n){"use strict";n.r(e);var o=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"content-body"},[n("div",{staticClass:"table-action"},[n("div",{staticClass:"lf"}),n("div",{staticClass:"rt"},[n("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-plus"},on:{click:t.addclick}},[t._v("新增")]),n("el-button",{attrs:{size:"small",type:"info",icon:"el-icon-edit"},on:{click:function(e){return t.handleEdit(t.valLength,t.multipleSelection)}}},[t._v("编辑")]),n("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:function(e){return t.deleteClick(t.valLength,t.multipleSelection)}}},[t._v("删除")])],1)]),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:t.loading,expression:"loading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{border:"",stripe:"",data:t.pageDataList,"tooltip-effect":"dark",height:t.tableHeight,"element-loading-text":"拼命加载中","element-loading-background":"rgba(255, 255, 255, 0.8)"},on:{"row-click":t.handleRowClick,"selection-change":t.handleSelectionChange}},[n("el-table-column",{attrs:{type:"selection",width:"80",fixed:""}}),n("el-table-column",{attrs:{label:"编号",type:"index",width:"80"}}),n("el-table-column",{attrs:{label:"公告内容",sortable:"",prop:"content",width:"300"},scopedSlots:t._u([{key:"default",fn:function(e){return[n("el-tooltip",{staticClass:"content",attrs:{effect:"light",content:e.row.content,placement:"top-start","show-overflow-tooltip":"true"}},[e.row.content.length>15?n("div",[t._v(" "+t._s(e.row.content.substring(0,15))+"... ")]):n("div",[t._v(t._s(e.row.content))])])]}}])}),n("el-table-column",{attrs:{label:"是否展示",sortable:"",prop:"whether_need",width:"120"},scopedSlots:t._u([{key:"default",fn:function(e){return[2==e.row.whether_need?n("div",[t._v("否")]):n("div",[t._v("是")])]}}])}),n("el-table-column",{attrs:{label:"创建人",sortable:"",prop:"creator",width:"100"}}),n("el-table-column",{attrs:{label:"创建时间",sortable:"",prop:"creation_time",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.creation_time?n("div",[t._v(" "+t._s(t.formatDate1(e.row.creation_time))+" ")]):t._e()]}}])}),n("el-table-column",{attrs:{label:"编辑人",sortable:"",prop:"editor",width:"100"}}),n("el-table-column",{attrs:{label:"最后一次编辑的时间",sortable:"",prop:"edit_session",width:"200"},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.edit_session?n("div",[t._v(" "+t._s(t.formatDate1(e.row.edit_session))+" ")]):t._e()]}}])})],1),n("div",{staticClass:"table-page__action"},[n("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next, jumper",total:t.pageCount,"page-size":15},on:{"current-change":t.changePage}})],1),n("el-dialog",{attrs:{title:"新增通知公告",width:"800px","close-on-click-modal":!1,visible:t.isaddmaskshow},on:{"update:visible":function(e){t.isaddmaskshow=e}}},[t.isaddmaskshow?n("TheForm",{ref:"newForm"}):t._e(),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{staticClass:"noadd",on:{click:t.noaddclick}},[t._v("取消")]),n("el-button",{staticClass:"okadd",attrs:{type:"primary"},on:{click:t.okaddclick}},[t._v("确认")])],1)],1),n("el-dialog",{attrs:{title:"编辑通知公告",width:"800px","close-on-click-modal":!1,visible:t.iseditmaskshow},on:{"update:visible":function(e){t.iseditmaskshow=e}}},[t.iseditmaskshow?n("TheForm",{ref:"editForm",attrs:{type:"edit"}}):t._e(),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{staticClass:"noedit",on:{click:t.noEditclick}},[t._v("取消")]),n("el-button",{staticClass:"okedit",attrs:{type:"primary"},on:{click:t.okEditclick}},[t._v("确认")])],1)],1)],1)},a=[],i=(n("a15b"),n("d3b7"),n("5530")),r=(n("96cf"),n("1da1")),s=n("4124"),l=function(){var t=this,e=t.$createElement,n=t._self._c||e;return t.form?n("el-form",{ref:"form",attrs:{"label-width":"100px",model:t.form,rules:t.rules}},[n("el-form-item",{attrs:{label:"公告内容",prop:"content"}},[n("el-input",{attrs:{type:"textarea",rows:7,placeholder:"请输入公告内容"},model:{value:t.form.content,callback:function(e){t.$set(t.form,"content",e)},expression:"form.content"}})],1),n("el-form-item",{attrs:{label:"是否展示",prop:"checked"}},[n("el-radio-group",{model:{value:t.form.checked,callback:function(e){t.$set(t.form,"checked",e)},expression:"form.checked"}},[n("el-radio",{attrs:{label:2}},[t._v("否")]),n("el-radio",{attrs:{label:1}},[t._v("是")])],1)],1)],1):t._e()},c=[],u=n("4260"),d={name:"",props:{type:{type:String,default:"new"}},data:function(){return{rules:{content:[{required:!0,message:"通知公告内容不能为空",trigger:"blur"}],checked:[{required:!0,message:"请选择是否展示",trigger:"change"}]},form:{content:"",checked:1,companyId:""},defaultForm:{}}},watch:{},created:function(){var t=sessionStorage.getItem("companyId",t);this.form.companyId=t,this.init()},mounted:function(){},methods:{isPass:function(){var t=this;return new Promise((function(e){var n=t.$refs.form;n.validate((function(t){e(t)}))}))},init:function(){this.defaultForm=Object(u["a"])(this.form)},getValue:function(){var t=Object(u["a"])(this.form);return t},setValue:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.form=Object(u["a"])(t)},reset:function(){this.form=Object(u["a"])(this.defaultForm),this.$refs.form.resetFields()},getColor:function(t){this.form.img_colour=t}},computed:{},filters:{}},m=d,f=n("2877"),h=Object(f["a"])(m,l,c,!1,null,"66231019",null),g=h.exports,p={components:{TheForm:g},data:function(){return{tableHeight:window.innerHeight-210,tableData:[],editContent:"",isaddmaskshow:!1,iseditmaskshow:!1,filePath:"",multipleSelection:[],valLength:"",selectUploadFileBtn:!0,pageCount:null,pageDataList:[],searchInfo:"",home_imgAll:[],schoolId:"",companyId:"",adminName:"",loading:!1}},methods:{formatDate1:function(){return u["c"].apply(void 0,arguments)},handleSelectionChange:function(t){this.multipleSelection=t,this.valLength=t.length},handleRowClick:function(t,e,n){this.$refs.multipleTable.toggleRowSelection(t)},addConsult:function(){var t=this;return Object(r["a"])(regeneratorRuntime.mark((function e(){var n,o;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,t.$refs.newForm.isPass();case 2:if(n=e.sent,0!=n){e.next=5;break}return e.abrupt("return");case 5:o=t.$refs.newForm.getValue(),t.loading=!0,Object(s["b"])(Object(i["a"])(Object(i["a"])({},o),{},{creator:t.adminName})).then((function(e){1==e.code?t.$message.error(e.msg):(t.isaddmaskshow=!1,t.$message.success(e.msg),t.getChangePage({page:1,limit:15}))})).finally((function(){t.loading=!1}));case 8:case"end":return e.stop()}}),e)})))()},addclick:function(){var t=this;this.isaddmaskshow=!0,this.$nextTick((function(){t.$refs.newForm.reset()}))},noaddclick:function(){this.isaddmaskshow=!1},okaddclick:function(){this.addConsult()},EditInfo:function(){var t=this;this.loading=!0;var e=this.$refs.editForm.getValue();Object(s["q"])(Object(i["a"])(Object(i["a"])({},e),{},{editor:this.adminName})).then((function(e){t.iseditmaskshow=!1,1==e.code?t.$message.error(e.msg):(t.$message.success(e.msg),t.getChangePage({page:1,limit:15}))})).finally((function(){t.loading=!1}))},handleEdit:function(t,e){var n=this;if(t){if(1==t)this.iseditmaskshow=!0,this.editContent=Object(u["a"])(e[0]),this.$nextTick((function(){n.$refs.editForm.reset(),n.$refs.editForm.setValue(n.editContent)}));else if(t>1)return void this.$message.error("您只能选择一个要编辑的内容")}else this.$message.error("您还未选择要编辑的内容")},okEditclick:function(){var t=this;return Object(r["a"])(regeneratorRuntime.mark((function e(){var n;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,t.$refs.editForm.isPass();case 2:if(n=e.sent,0!=n){e.next=5;break}return e.abrupt("return");case 5:t.iseditmaskshow=!1,t.EditInfo();case 7:case"end":return e.stop()}}),e)})))()},noEditclick:function(){this.iseditmaskshow=!1},deleteClick:function(t,e){var n=this;if(t){for(var o=[],a=0;a<e.length;a++){var i=e[a].bulletinBoardId;o.push(i)}this.$confirm("确认要删除吗？").then((function(){n.loading=!0,Object(s["g"])({bulletinBoardIds:o.join(",")}).then((function(t){1==t.code?n.$message.error(t.msg):(n.$message.success(t.msg),n.getChangePage({page:1,limit:15}))})).finally((function(){n.loading=!1}))}))}else this.$message.error("您还未选择要删除的内容")},getChangePage:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,Object(s["l"])(Object(i["a"])(Object(i["a"])({},e),{},{companyId:this.companyId})).then((function(e){var n=e.data;t.pageCount=e.pageCount,t.pageDataList=e.data;for(var o=[],a=0;a<n.length;a++){var i=n[a].home_img;o.push(i)}t.home_imgAll=o})).finally((function(){t.loading=!1}))},changePage:function(t){this.getChangePage({page:t,limit:15})},searchStudentInfo:function(){this.getChangePage({studentName:this.searchInfo})}},mounted:function(){var t=sessionStorage.getItem("companyId",t);this.companyId=t;var e=sessionStorage.getItem("schoolId",e);this.schoolId=e;var n=sessionStorage.getItem("adminName",n);this.adminName=n,this.getChangePage({page:1,limit:15})}},b=p,v=(n("b8d0"),n("64e4"),Object(f["a"])(b,o,a,!1,null,"2661bde3",null));e["default"]=v.exports}}]);