(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2ef6bb36"],{"3b22":function(e,t,a){"use strict";a.d(t,"c",(function(){return i})),a.d(t,"j",(function(){return n})),a.d(t,"d",(function(){return r})),a.d(t,"g",(function(){return s})),a.d(t,"a",(function(){return l})),a.d(t,"h",(function(){return c})),a.d(t,"e",(function(){return d})),a.d(t,"f",(function(){return u})),a.d(t,"i",(function(){return m})),a.d(t,"b",(function(){return p}));var o=a("b775");function i(e){return Object(o["a"])({url:"/addXqType",method:"post",data:e})}function n(e){return Object(o["a"])({url:"/updateXqType",method:"post",data:e})}function r(e){return Object(o["a"])({url:"/deleteManyXqType",method:"get",params:e})}function s(e){return Object(o["a"])({url:"/queryXqTypes",method:"get",params:e})}function l(e){return Object(o["a"])({url:"/addParam",method:"post",data:e})}function c(e){return Object(o["a"])({url:"/updateParam",method:"post",data:e})}function d(e){return Object(o["a"])({url:"/deleteParamByParamId",method:"get",params:e})}function u(e){return Object(o["a"])({url:"/limitParam",method:"get",params:e})}function m(e){return Object(o["a"])({url:"/updateParamShowByParamId",method:"get",params:e})}function p(e){return Object(o["a"])({url:"/addRecordParam",method:"post",data:e})}},"710f":function(e,t,a){"use strict";a.r(t);var o=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"content-body"},[a("div",{staticClass:"table-action"},[a("div",{staticClass:"lf"},[a("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入关键字进行查询",size:"small"},on:{input:e.goSearch},model:{value:e.searchInfo,callback:function(t){e.searchInfo=t},expression:"searchInfo"}},[a("el-button",{attrs:{slot:"append",icon:"el-icon-search"},on:{click:e.searchClassInfo},slot:"append"})],1)],1),a("div",{staticClass:"rt"},[a("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-plus"},on:{click:e.addclick}},[e._v("新增")]),a("el-button",{attrs:{size:"small",type:"info",icon:"el-icon-edit"},on:{click:function(t){return e.handleEdit(e.valLength,e.multipleSelection)}}},[e._v("编辑")]),a("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:function(t){return e.deleteClick(e.valLength,e.multipleSelection)}}},[e._v("删除")])],1)]),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{border:"",stripe:"","element-loading-text":"拼命加载中","element-loading-background":"rgba(255, 255, 255, 0.8)",data:e.pageDataList,"tooltip-effect":"dark",height:e.tableHeight},on:{"row-click":e.handleRowClick,"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"100",fixed:""}}),a("el-table-column",{attrs:{label:"编号",type:"index",width:"100"}}),a("el-table-column",{attrs:{label:"类别",prop:"audioType",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[1==t.row.audioType?a("el-tag",{attrs:{type:"1"===t.row.audioType?"success":"primary","disable-transitions":""}},[e._v("一般")]):e._e(),2==t.row.audioType?a("el-tag",{attrs:{type:"2"===t.row.audioType?"primary":"success","disable-transitions":""}},[e._v("很棒")]):e._e(),3==t.row.audioType?a("el-tag",{attrs:{type:"3"===t.row.audioType?"primary":"danger","disable-transitions":""}},[e._v("完美")]):e._e()]}}])}),a("el-table-column",{attrs:{label:"音频链接",prop:"paramName",width:"500"}}),a("el-table-column",{attrs:{label:"创建人",sortable:"",prop:"creator",width:"200"}}),a("el-table-column",{attrs:{label:"创建时间",sortable:"",prop:"creationTime",width:"200"}}),a("el-table-column",{attrs:{label:"编辑人",sortable:"",prop:"editor",width:"200"}}),a("el-table-column",{attrs:{label:"最后一次编辑时间",sortable:"",prop:"editSession",width:"200"}})],1),a("div",{staticClass:"table-page__action"},[a("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next, jumper",total:e.pageCount,"page-size":20},on:{"current-change":e.changePage}})],1),a("el-dialog",{attrs:{title:"新增评测结果提示音",width:"600px","close-on-click-modal":!1,visible:e.isaddmaskshow},on:{"update:visible":function(t){e.isaddmaskshow=t}}},[a("el-form",{ref:"addForm",attrs:{"label-width":"150px",model:e.addForm,rules:e.rules}},[a("el-form-item",{attrs:{label:"一般(提示音):",prop:"chaRecording"}},[a("upload",{attrs:{audio:"",params:{companyId:e.companyId}},model:{value:e.addForm.chaRecording,callback:function(t){e.$set(e.addForm,"chaRecording",t)},expression:"addForm.chaRecording"}}),a("div",{},[e._v("(只能上传一个范读音频文件)")])],1),a("el-form-item",{attrs:{label:"很棒(提示音):",prop:"goodRecording"}},[a("upload",{attrs:{audio:"",params:{companyId:e.companyId}},model:{value:e.addForm.goodRecording,callback:function(t){e.$set(e.addForm,"goodRecording",t)},expression:"addForm.goodRecording"}}),a("div",{},[e._v("(只能上传一个范读音频文件)")])],1),a("el-form-item",{attrs:{label:"完美(提示音):",prop:"perfectRecording"}},[a("upload",{attrs:{audio:"",params:{companyId:e.companyId}},model:{value:e.addForm.perfectRecording,callback:function(t){e.$set(e.addForm,"perfectRecording",t)},expression:"addForm.perfectRecording"}}),a("div",{},[e._v("(只能上传一个范读音频文件)")])],1)],1),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"noadd",on:{click:e.noaddclick}},[e._v("取消")]),a("el-button",{staticClass:"okadd",attrs:{type:"primary"},on:{click:e.okaddclick}},[e._v("确认")])],1)],1),a("el-dialog",{attrs:{title:"编辑提示音类型信息",width:"600px","close-on-click-modal":!1,visible:e.iseditmaskshow},on:{"update:visible":function(t){e.iseditmaskshow=t}}},[e.editContent?a("el-form",{ref:"editForm",attrs:{"label-width":"150px",model:e.editContent,rules:e.rules}},[a("el-form-item",{attrs:{label:"提示音:",prop:"paramName"}},[a("upload",{attrs:{audio:"",params:{companyId:e.companyId}},model:{value:e.editContent.paramName,callback:function(t){e.$set(e.editContent,"paramName",t)},expression:"editContent.paramName"}}),a("div",{},[e._v("(只能上传一个范读音频文件)")])],1)],1):e._e(),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"noedit",on:{click:e.noeditclick}},[e._v("取消")]),a("el-button",{staticClass:"okedit",attrs:{type:"primary"},on:{click:e.okeditclick}},[e._v("确认")])],1)],1),a("el-dialog",{attrs:{title:"选择是否开通此功能",width:"600px","close-on-click-modal":!1,visible:e.isOpeningMaskShow},on:{"update:visible":function(t){e.isOpeningMaskShow=t}}},[e.OpeningContent?a("el-form",{ref:"OpeningForm",attrs:{"label-width":"80px",rules:e.rules,model:e.OpeningContent}},[a("el-form-item",{attrs:{prop:"paramShow"}},[a("el-radio-group",{model:{value:e.OpeningContent.paramShow,callback:function(t){e.$set(e.OpeningContent,"paramShow",t)},expression:"OpeningContent.paramShow"}},[a("el-radio",{attrs:{label:0}},[e._v("否")]),a("el-radio",{attrs:{label:1}},[e._v("是")])],1)],1)],1):e._e(),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{staticClass:"no",on:{click:e.noOpeningclick}},[e._v("取消")]),a("el-button",{staticClass:"ok",attrs:{type:"primary"},on:{click:e.okOpeningclick}},[e._v("确认")])],1)],1)],1)},i=[],n=(a("a15b"),a("d3b7"),a("5530")),r=(a("96cf"),a("1da1")),s=a("9dac"),l=a("3b22"),c=a("4260"),d={data:function(){return{rules:{chaRecording:[{required:!0,message:"请上传音频",trigger:"blur"}],goodRecording:[{required:!0,message:"请上传音频",trigger:"blur"}],perfectRecording:[{required:!0,message:"请上传音频",trigger:"blur"}],paramName:[{required:!0,message:"请上传音频",trigger:"blur"}]},tableHeight:window.innerHeight-210,tableData:[],isaddmaskshow:!1,iseditmaskshow:!1,editContent:"",addForm:{chaRecording:"",goodRecording:"",perfectRecording:""},multipleSelection:[],valLength:"",timeout:null,searchInfo:"",pageCount:null,pageDataList:[],classImgAll:[],schoolId:"",companyId:"",loading:!1,adminName:"",OpeningContent:{paramShow:0},isOpeningMaskShow:!1,publicAudioPath:"",chaAudioList:[],goodAudioList:[],perfectAudioList:[]}},components:{upload:s["a"]},methods:{goSearch:function(){var e=this;this.searchInfo;clearTimeout(this.timeout),this.timeout=setTimeout((function(){e.getChangePage({paramName:e.searchInfo,page:1,limit:20})}),300)},isPass:function(e){var t=this;return new Promise((function(a){var o=t.$refs[e];o.validate((function(e){a(e)}))}))},resetForm:function(e){void 0!==this.$refs[e]&&this.$refs[e].resetFields()},handleRowClick:function(e,t,a){this.$refs.multipleTable.toggleRowSelection(e)},addClass:function(e){var t=this;return Object(r["a"])(regeneratorRuntime.mark((function a(){var o;return regeneratorRuntime.wrap((function(a){while(1)switch(a.prev=a.next){case 0:return a.next=2,t.isPass("addForm");case 2:if(o=a.sent,0!=o){a.next=5;break}return a.abrupt("return");case 5:t.loading=!0,Object(l["b"])(e).then((function(e){1==e.code?t.$message.error(e.msg):(t.isaddmaskshow=!1,t.$message.success(e.msg),t.getChangePage({page:1,limit:20}))})).finally((function(){t.loading=!1}));case 7:case"end":return a.stop()}}),a)})))()},addclick:function(){3==this.pageDataList.length?this.$message.error("每个类别各自只能添加一条,请选择删除或者编辑再进行操作"):(this.isaddmaskshow=!0,this.resetForm("addForm"))},noaddclick:function(){this.isaddmaskshow=!1},okaddclick:function(){if(""!=this.addForm.chaRecording&&""!=this.addForm.goodRecording&&""!=this.addForm.perfectRecording){var e=[],t=this.schoolId,a=this.adminName;e.push({paramName:this.addForm.chaRecording,paramSchoolId:t,creator:a,paramType:"record",audioType:1}),e.push({paramName:this.addForm.goodRecording,paramSchoolId:t,creator:a,paramType:"record",audioType:2}),e.push({paramName:this.addForm.perfectRecording,paramSchoolId:t,creator:a,paramType:"record",audioType:3}),this.addClass(e)}else this.$message.error("请将每个类别提示语音添加完整")},handleEdit:function(e,t){e?1==e?(this.iseditmaskshow=!0,this.editContent=Object(c["a"])(t[0]),this.resetForm("editForm")):e>1&&this.$message.error("您只能选择一个要编辑的内容"):this.$message.error("您还未选择要编辑的内容")},EditClass:function(){var e=this;this.loading=!0,Object(l["h"])(Object(n["a"])(Object(n["a"])({},this.editContent),{},{paramSchoolId:this.schoolId,editor:this.adminName,paramType:"record"})).then((function(t){1==t.code?e.$message.error(t.msg):(e.iseditmaskshow=!1,e.$message.success(t.msg),e.getChangePage({page:1,limit:20}))})).finally((function(){e.loading=!1}))},okeditclick:function(){var e=this;return Object(r["a"])(regeneratorRuntime.mark((function t(){var a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.isPass("editForm");case 2:if(a=t.sent,0!=a){t.next=5;break}return t.abrupt("return");case 5:e.EditClass();case 6:case"end":return t.stop()}}),t)})))()},noeditclick:function(){this.iseditmaskshow=!1},handleSelectionChange:function(e){this.multipleSelection=e,this.valLength=e.length},deleteClick:function(e,t){for(var a=this,o=[],i=0;i<this.pageDataList.length;i++){var n=this.pageDataList[i].paramId;o.push(n)}this.$confirm("确认要删除所有提示语音吗,如果想修改其中一个，可点击编辑进行操作!").then((function(){a.loading=!0,Object(l["e"])({paramIds:o.join(",")}).then((function(e){1==e.code?a.$message.error(e.msg):a.$message.success(e.msg),a.getChangePage({page:1,limit:20})})).finally((function(){a.loading=!1}))}))},getChangePage:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,Object(l["f"])(Object(n["a"])(Object(n["a"])({page:1,limit:20},t),{},{paramSchoolId:this.schoolId,paramType:"record"})).then((function(t){1==t.code?(e.pageCount=0,e.pageDataList=[]):(e.pageCount=t.pageCount,e.pageDataList=t.data)})).finally((function(){e.loading=!1}))},changePage:function(e){this.getChangePage({page:e,limit:20})},searchClassInfo:function(){this.getChangePage({paramName:this.searchInfo,page:1,limit:20})},handleOpening:function(e,t){this.isOpeningMaskShow=!0},noOpeningclick:function(){this.isOpeningMaskShow=!1},okOpeningclick:function(){this.getOpening()},getOpening:function(){var e=this;this.loading=!0,Object(l["i"])(Object(n["a"])({},this.OpeningContent)).then((function(t){1==t.code?e.$message.error(t.msg):(e.$message.success(t.msg),e.isOpeningMaskShow=!1,e.getChangePage({page:1,limit:20}))})).finally((function(){e.loading=!1}))}},mounted:function(){var e=sessionStorage.getItem("schoolId",e);this.schoolId=e;var t=sessionStorage.getItem("adminName",t);this.adminName=t;var a=sessionStorage.getItem("companyId",a);this.companyId=a,this.getChangePage({page:1,limit:20})}},u=d,m=(a("9fe6"),a("2877")),p=Object(m["a"])(u,o,i,!1,null,"6f0bd69c",null);t["default"]=p.exports},"9fe6":function(e,t,a){"use strict";a("a492")},a492:function(e,t,a){}}]);