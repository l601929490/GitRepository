(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-a38abb78"],{"2e87":function(e,t,o){},"5d8b":function(e,t,o){},"79da":function(e,t,o){"use strict";o.r(t);var a=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"content-body"},[o("div",{staticClass:"table-action"},[o("div",{staticClass:"lf"},[o("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入分校区名称进行查询",size:"small"},model:{value:e.searchInfo,callback:function(t){e.searchInfo=t},expression:"searchInfo"}},[o("el-button",{attrs:{slot:"append",icon:"el-icon-search"},on:{click:e.searchClassInfo},slot:"append"})],1)],1),o("div",{staticClass:"rt"},[o("el-button",{attrs:{type:"primary",size:"small",icon:"el-icon-plus"},on:{click:e.addclick}},[e._v("新增")]),o("el-button",{attrs:{size:"small",type:"info",icon:"el-icon-edit"},on:{click:function(t){return e.handleEdit(e.valLength,e.multipleSelection)}}},[e._v("编辑")]),o("el-button",{attrs:{type:"danger",size:"small",icon:"el-icon-delete"},on:{click:function(t){return e.deleteClick(e.valLength,e.multipleSelection)}}},[e._v("删除")])],1)]),o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"multipleTable",staticStyle:{width:"100%"},attrs:{border:"",stripe:"",data:e.pageDataList,"tooltip-effect":"dark",height:e.tableHeight,"element-loading-text":"拼命加载中","element-loading-background":"rgba(255, 255, 255, 0.8)"},on:{"row-click":e.handleRowClick,"selection-change":e.handleSelectionChange}},[o("el-table-column",{attrs:{type:"selection",width:"100",fixed:""}}),o("el-table-column",{attrs:{label:"编号",type:"index",width:"100"}}),o("el-table-column",{attrs:{label:"校区名称",prop:"schoolName",width:"150"}}),o("el-table-column",{attrs:{label:"校区电话",prop:"schoolPhone",width:"150"}}),o("el-table-column",{attrs:{label:"负责人姓名",prop:"schoolPrincipal",width:"100"}}),o("el-table-column",{attrs:{label:"负责人电话",prop:"schoolPrincipalPhone",width:"150"}}),o("el-table-column",{attrs:{label:"培训类型",sortable:"",prop:"schoolType",width:"120"}}),o("el-table-column",{attrs:{label:"地理位置",prop:"schoolAddress",width:"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-tooltip",{staticClass:"item",attrs:{effect:"light",content:t.row.schoolAddress,placement:"top-start"}},[t.row.schoolAddress.length>8?o("div",[e._v(" "+e._s(t.row.schoolAddress.substring(0,8))+"... ")]):o("div",[e._v(e._s(t.row.schoolAddress))])])]}}])}),o("el-table-column",{attrs:{label:"坐标经度",sortable:"",prop:"schoolLongitude",width:"170"}}),o("el-table-column",{attrs:{label:"坐标纬度",sortable:"",prop:"schoolLatitude",width:"170"}}),o("el-table-column",{attrs:{label:"校区简介",sortable:"",prop:"schoolSynopsis",width:"200"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-tooltip",{staticClass:"schoolSynopsis",attrs:{effect:"light",content:e._f("toRemoveCharacter")(t.row.schoolSynopsis),placement:"top-start","show-overflow-tooltip":"true"}},[t.row.schoolSynopsis.length>10?o("div",[e._v(" "+e._s(e._f("toRemoveCharacter")(t.row.schoolSynopsis.substring(0,10)))+"... ")]):o("div",[e._v(e._s(e._f("toRemoveCharacter")(t.row.schoolSynopsis)))])])]}}])}),o("el-table-column",{attrs:{label:"创建人",sortable:"",prop:"creator",width:"100"}}),o("el-table-column",{attrs:{label:"创建时间",sortable:"",prop:"creationTime",width:"160"}}),o("el-table-column",{attrs:{label:"编辑人",sortable:"",prop:"editor",width:"100"}}),o("el-table-column",{attrs:{label:"最后一次编辑时间",sortable:"",prop:"editSession",width:"160"}})],1),o("div",{staticClass:"table-page__action"},[o("el-pagination",{attrs:{background:"",layout:"total, prev, pager, next, jumper",total:e.pageCount,"page-size":10},on:{"current-change":e.changePage}})],1),o("el-dialog",{attrs:{title:"新增校区信息",width:"900px","close-on-click-modal":!1,visible:e.isaddmaskshow},on:{"update:visible":function(t){e.isaddmaskshow=t}}},[e.isaddmaskshow?o("TheForm",{ref:"newForm"}):e._e(),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{staticClass:"noadd",on:{click:e.noaddclick}},[e._v("取消")]),o("el-button",{staticClass:"okadd",attrs:{type:"primary"},on:{click:e.okaddclick}},[e._v("确认")])],1)],1),o("el-dialog",{attrs:{title:"编辑信息",width:"900px","close-on-click-modal":!1,visible:e.iseditmaskshow},on:{"update:visible":function(t){e.iseditmaskshow=t}}},[e.iseditmaskshow?o("TheForm",{ref:"editForm",attrs:{type:"edit"}}):e._e(),o("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{staticClass:"noedit",on:{click:e.noeditclick}},[e._v("取消")]),o("el-button",{staticClass:"okedit",attrs:{type:"primary"},on:{click:e.okeditclick}},[e._v("确认")])],1)],1)],1)},s=[],n=(o("a15b"),o("d3b7"),o("ac1f"),o("5319"),o("5530")),i=(o("96cf"),o("1da1")),l=o("9dac"),r=o("caa4"),c=o("4260"),d=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("el-form",{ref:"form",attrs:{"label-width":"100px",model:e.form,rules:e.rules}},[o("el-form-item",{attrs:{label:"校区名称",prop:"schoolName"}},[o("el-input",{attrs:{placeholder:"请输入校区名称"},model:{value:e.form.schoolName,callback:function(t){e.$set(e.form,"schoolName",t)},expression:"form.schoolName"}})],1),o("el-form-item",{attrs:{label:"校区电话",prop:"schoolPhone"}},[o("el-input",{attrs:{placeholder:"请输入校区电话"},model:{value:e.form.schoolPhone,callback:function(t){e.$set(e.form,"schoolPhone",t)},expression:"form.schoolPhone"}})],1),o("el-form-item",{attrs:{label:"负责人姓名",prop:"schoolPrincipal"}},[o("el-input",{attrs:{placeholder:"请输入负责人姓名"},model:{value:e.form.schoolPrincipal,callback:function(t){e.$set(e.form,"schoolPrincipal",t)},expression:"form.schoolPrincipal"}})],1),o("el-form-item",{attrs:{label:"负责人电话",prop:"schoolPrincipalPhone"}},[o("el-input",{attrs:{placeholder:"请输入负责人电话"},model:{value:e.form.schoolPrincipalPhone,callback:function(t){e.$set(e.form,"schoolPrincipalPhone",t)},expression:"form.schoolPrincipalPhone"}})],1),o("el-form-item",{attrs:{label:"培训类型",prop:"schoolType"}},[o("el-select",{attrs:{placeholder:"请选择培训类型"},model:{value:e.form.schoolType,callback:function(t){e.$set(e.form,"schoolType",t)},expression:"form.schoolType"}},e._l(e.subjectList,(function(e,t){return o("el-option",{key:t,attrs:{value:e}})})),1)],1),o("el-form-item",{attrs:{label:"坐标经度",prop:"schoolLongitude"}},[o("el-input",{attrs:{disabled:"",placeholder:"请获取坐标经度(输入地址后即可获取)"},model:{value:e.form.schoolLongitude,callback:function(t){e.$set(e.form,"schoolLongitude",t)},expression:"form.schoolLongitude"}})],1),o("el-form-item",{attrs:{label:"坐标纬度",prop:"schoolLatitude"}},[o("el-input",{attrs:{disabled:"",placeholder:"请获取坐标纬度(输入地址后即可获取)"},model:{value:e.form.schoolLatitude,callback:function(t){e.$set(e.form,"schoolLatitude",t)},expression:"form.schoolLatitude"}})],1),o("el-form-item",{attrs:{label:"地理位置"}},[o("div",{staticStyle:{display:"flex"}},[o("el-input",{attrs:{placeholder:"请输入详细的地理位置(输入即可自动检索或点击自动获取)"},on:{input:e.searchAddress},model:{value:e.form.schoolAddress,callback:function(t){e.$set(e.form,"schoolAddress",t)},expression:"form.schoolAddress"}}),o("el-button",{staticStyle:{"margin-left":"10px"},attrs:{type:"primary",disabled:!e.schoolAddressByMap},on:{click:e.setSchoolAddressByMap}},[e._v("自动获取")])],1),o("div",{staticClass:"tableMap"},[o("baiduMap",{ref:"baiduMap",attrs:{value:e.point},on:{search:e.onSearchMap,click:e.clickMap}})],1)]),o("el-form-item",{attrs:{label:"校区介绍",prop:"schoolSynopsis"}},[o("editor",{model:{value:e.form.schoolSynopsis,callback:function(t){e.$set(e.form,"schoolSynopsis",t)},expression:"form.schoolSynopsis"}})],1)],1)},h=[],u=(o("841c"),function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticStyle:{width:"100%",height:"100%","min-height":"100px"},attrs:{id:"map"}})}),m=[],p=(o("d81d"),function(e,t){return new BMap.Point(108.89250946331023,34.23591859393846)}),f={props:{value:Object},data:function(){return{map:null,point:null,address:null,local:null}},watch:{value:function(){this.point=this.value}},mounted:function(){var e=this.map=new BMap.Map("map");this.setPoint(108.89250946331023,34.23591859393846),e.enableScrollWheelZoom(!0),this.initBindEvent()},methods:{emitClick:function(){this.$emit("click",{point:this.point,address:this.address})},addMarker:function(e,t){var o=this.map,a=new BMap.Icon("http://api0.map.bdimg.com/images/marker_red_sprite.png",new BMap.Size(23,25),{anchor:new BMap.Size(10,25),imageOffset:new BMap.Size(0,-25)}),s=new BMap.Marker(p(e,t),{icon:a});o.addOverlay(s)},setPoint:function(e,t){this.point=p(e,t),this.map.centerAndZoom(this.point,17),this.addMarker(e,t)},search:function(e){var t=this,o=this.map;this.local&&this.local.clearResults(),this.local=new BMap.LocalSearch(o,{renderOptions:{map:o},onSearchComplete:function(e){if(t.local.getStatus()==BMAP_STATUS_SUCCESS){for(var o=[],a=0;a<e.getCurrentNumPois();a++)o.push(e.getPoi(a));t.$emit("search",o)}}}),this.local.search(e)},initBindEvent:function(){var e=this,t=new BMap.Geocoder,o=this.map;o.addEventListener("click",(function(o){var a=o.point;console.log("e",o),t.getLocation(a,(function(t){e.point=a,e.address=t,e.emitClick()}))}))}}},g=f,b=o("2877"),v=Object(b["a"])(g,u,m,!1,null,"3879e2c9",null),w=v.exports,k=o("ceb0"),y={props:{type:{type:String,default:"new"}},components:{baiduMap:w,Editor:k["a"]},data:function(){return{rules:{schoolName:[{required:!0,message:"校区名称不能为空",trigger:"blur"},{min:2,max:10,message:"校区名称长度在3-10位之间，请检查",trigger:"blur"}],schoolPhone:[{required:!0,message:"校区电话不能为空",trigger:"blur"},{trigger:"blur",validator:function(e,t,o){console.log("value",t),/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/.test(t)?o():o(new Error("请输入正确的11位手机号"))}}],schoolPrincipal:[{required:!0,message:"负责人姓名不能为空",trigger:"blur"},{min:2,max:4,message:"负责人姓名长度在2-4位之间，请检查",trigger:"blur"}],schoolPrincipalPhone:[{required:!0,message:"负责人电话不能为空",trigger:"blur"},{trigger:"blur",validator:function(e,t,o){console.log("value",t),/^1[3456789]\d{9}$/.test(t)?o():o(new Error("请输入正确的11位手机号"))}}],schoolAddress:[{required:!0,message:"地理位置不能为空",trigger:"change"}],schoolLongitude:[{required:!0,message:"坐标经度不能为空",trigger:"change"}],schoolLatitude:[{required:!0,message:"坐标纬度不能为空",trigger:"change"}],schoolSynopsis:[{required:!0,message:"校区介绍不能为空",trigger:"change"}]},form:{schoolAddress:"",schoolLongitude:"",schoolLatitude:"",schoolName:"",schoolPhone:"",schoolPrincipal:"",schoolPrincipalPhone:"",schoolType:"",schoolSynopsis:""},subjectList:[],point:{lng:108.898137,lat:34.242424},address:"",lat:"",lng:"",defaultForm:{},schoolAddressByMap:""}},methods:{isPass:function(){var e=this;return new Promise((function(t){var o=e.$refs.form;o.validate((function(e){t(e)}))}))},init:function(){this.defaultForm=Object(c["a"])(this.form)},getValue:function(){var e=Object(c["a"])(this.form);return e},setValue:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.form=Object(c["a"])(e),console.log("this.form",this.form)},reset:function(){this.form=Object(c["a"])(this.defaultForm),this.$refs.form.resetFields(),this.schoolAddressByMap=""},searchAddress:function(){var e=this;clearTimeout(window.searchAddressTIMER),window.searchAddressTIMER=setTimeout((function(){var t=e.form.schoolAddress,o=e.$refs.baiduMap;o.search(t)}),300)},getBasicParameters:function(){var e=this;Object(r["j"])({schoolId:this.schoolId,paramType:"TrainingType"}).then((function(t){for(var o=t.data,a=[],s=0;s<o.length;s++)a.push(o[s].paramName);e.subjectList=a,e.form.schoolType=a[0]}))},clickMap:function(e){console.log(e)},onSearchMap:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[],t=e[0];t&&(this.schoolAddressByMap=t.address+"("+t.title+")",this.form.schoolLatitude=t.point.lat,this.form.schoolLongitude=t.point.lng)},setSchoolAddressByMap:function(){this.form.schoolAddress=this.schoolAddressByMap}},created:function(){var e=sessionStorage.getItem("schoolId",e);this.schoolId=e,this.init(),this.getBasicParameters()}},S=y,C=(o("e330"),Object(b["a"])(S,d,h,!1,null,"0a8c8dc3",null)),P=C.exports,j={data:function(){return{tableHeight:window.innerHeight-210,tableData:[],isaddmaskshow:!1,iseditmaskshow:!1,editContent:"",multipleSelection:[],valLength:"",searchInfo:"",pageCount:null,pageDataList:[],classImgAll:[],imageurl:"",companyId:"",schoolId:"",subjectList:"",adminName:"",loading:!1,point:{lng:108.898137,lat:34.242424},address:"",lat:"",lng:""}},components:{upload:l["a"],TheForm:P},filters:{toRemoveCharacter:function(e){return(e+"").replace(/<p>|<\/p>|<p>|<br>|<br\/>/g,"")}},methods:{addClass:function(){var e=this;return Object(i["a"])(regeneratorRuntime.mark((function t(){var o,a;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$refs.newForm.isPass();case 2:if(o=t.sent,0!=o){t.next=5;break}return t.abrupt("return");case 5:e.loading=!0,a=e.$refs.newForm.getValue(),Object(r["a"])(Object(n["a"])(Object(n["a"])({},a),{},{companyId:e.companyId,creator:e.adminName})).then((function(t){1==t.code?e.$message.error(t.msg):(e.isaddmaskshow=!1,e.$message.success(t.msg),e.getChangePage({page:1,limit:10}))})).finally((function(){e.loading=!1}));case 8:case"end":return t.stop()}}),t)})))()},addclick:function(){var e=this;this.isaddmaskshow=!0,this.$nextTick((function(){e.$refs.newForm.reset()}))},noaddclick:function(){this.isaddmaskshow=!1},okaddclick:function(){this.addClass()},handleEdit:function(e,t){var o=this;e?1==e?(this.editContent=Object(c["a"])(t[0]),this.iseditmaskshow=!0,this.$nextTick((function(){o.$refs.editForm.reset(),o.$refs.editForm.setValue(o.editContent)}))):e>1&&this.$message.error("您只能选择一个要编辑的内容"):this.$message.error("您还未选择要编辑的内容")},EditClass:function(){var e=this;this.loading=!0;var t=this.$refs.editForm.getValue();Object(r["k"])(Object(n["a"])(Object(n["a"])({},t),{},{companyId:this.companyId,editor:this.adminName})).then((function(t){1==t.code?e.$message.error(t.msg):(e.iseditmaskshow=!1,e.$message.success(t.msg),e.getChangePage({page:1,limit:10}))})).finally((function(){e.loading=!1}))},okeditclick:function(){var e=this;return Object(i["a"])(regeneratorRuntime.mark((function t(){var o;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,e.$refs.editForm.isPass();case 2:if(o=t.sent,0!=o){t.next=5;break}return t.abrupt("return");case 5:e.EditClass();case 6:case"end":return t.stop()}}),t)})))()},noeditclick:function(){this.iseditmaskshow=!1},handleSelectionChange:function(e){this.multipleSelection=e,this.valLength=e.length},handleRowClick:function(e,t,o){this.$refs.multipleTable.toggleRowSelection(e)},deleteClick:function(e,t){var o=this;if(e){for(var a=[],s=0;s<t.length;s++){var n=t[s].schoolId;a.push(n)}this.$confirm("确认要删除吗？").then((function(){o.loading=!0,Object(r["d"])({schoolIds:a.join(",")}).then((function(e){1==e.code?o.$message.error(e.msg):o.$message.success(e.msg),o.getChangePage({page:1,limit:10})})).finally((function(){o.loading=!1}))}))}else this.$message.error("您还未选择要删除的内容")},getChangePage:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};this.loading=!0,Object(r["g"])(Object(n["a"])(Object(n["a"])({},t),{},{companyId:this.companyId})).then((function(t){e.pageCount=t.pageCount,e.pageDataList=t.data})).finally((function(){e.loading=!1}))},changePage:function(e){this.getChangePage({page:e,limit:10})},searchClassInfo:function(){this.getChangePage({className:this.searchInfo})},getImageurl:function(e){this.imageurl=e}},mounted:function(){var e=sessionStorage.getItem("companyId",e);this.companyId=e;var t=sessionStorage.getItem("schoolId",t);this.schoolId=t;var o=sessionStorage.getItem("adminName",o);this.adminName=o,this.getChangePage({page:1,limit:10})}},$=j,O=(o("8d3b"),Object(b["a"])($,a,s,!1,null,"1d7e1814",null));t["default"]=O.exports},"8d3b":function(e,t,o){"use strict";o("2e87")},caa4:function(e,t,o){"use strict";o.d(t,"a",(function(){return s})),o.d(t,"k",(function(){return n})),o.d(t,"d",(function(){return i})),o.d(t,"g",(function(){return l})),o.d(t,"j",(function(){return r})),o.d(t,"c",(function(){return c})),o.d(t,"m",(function(){return d})),o.d(t,"f",(function(){return h})),o.d(t,"i",(function(){return u})),o.d(t,"b",(function(){return m})),o.d(t,"l",(function(){return p})),o.d(t,"e",(function(){return f})),o.d(t,"h",(function(){return g}));var a=o("b775");function s(e){return Object(a["a"])({url:"/addSchool",method:"post",data:e})}function n(e){return Object(a["a"])({url:"/updateSchool",method:"post",data:e})}function i(e){return Object(a["a"])({url:"/deleteManySchool",method:"get",params:e})}function l(e){return Object(a["a"])({url:"/limitSchool",method:"get",params:e})}function r(e){return Object(a["a"])({url:"/queryParamBySchoolId",method:"get",params:e})}function c(e){return Object(a["a"])({url:"/addSchoolSlideshow",method:"post",data:e})}function d(e){return Object(a["a"])({url:"/updateSchoolSlideshow",method:"post",data:e})}function h(e){return Object(a["a"])({url:"/deleteSchoolSlideshowIds",method:"get",params:e})}function u(e){return Object(a["a"])({url:"/limitSchoolSlideshow",method:"get",params:e})}function m(e){return Object(a["a"])({url:"/addSchoolHonor",method:"post",data:e})}function p(e){return Object(a["a"])({url:"/updateSchoolHonor",method:"post",data:e})}function f(e){return Object(a["a"])({url:"/deleteSchoolHonorIds",method:"get",params:e})}function g(e){return Object(a["a"])({url:"/limitSchoolHonor",method:"get",params:e})}},e330:function(e,t,o){"use strict";o("5d8b")}}]);