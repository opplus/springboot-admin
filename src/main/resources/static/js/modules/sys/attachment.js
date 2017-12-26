$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/attachment/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', key: true, hidden: true },
			{ label: '标题', name: 'title', width: 100 },
            { label: '缩略图', name: 'path', width: 100, formatter: function(value, options, row){
                var mime=row.mimeType;
                if(mime.indexOf('image') >= 0){
                    return '<img class="img-thumbnail" style="width: 60px;height: 60px;" src="'+uploadFileResource+value+'" >';
                }else if(mime.indexOf('audio') >= 0){
                    return '<img class="img-thumbnail" style="width: 60px;height: 60px;" src="'+baseURL+'/image/audio.jpg" >';
                }else if(mime.indexOf('video') >= 0){
                    return '<img class="img-thumbnail" style="width: 60px;height: 60px;" src="'+baseURL+'/image/video.jpg" >';
                }else if(mime.indexOf('application') >= 0){
                    return '<img class="img-thumbnail" style="width: 60px;height: 60px;" src="'+baseURL+'/image/file.jpg" >';
                }
            } },
			{ label: '后缀', name: 'suffix', width: 50 },
            { label: '类型', name: 'mimeType', index: 'mime_type',width: 60 },
            { label: '创建时间', name: 'createTime', index: 'create_time',width: 90 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });

    new AjaxUpload('#upload', {
        action: baseURL + '/sys/attachment/upload?X-Token=' + token,
        name: 'file',
        autoSubmit:true,
        responseType:"json",
        onSubmit:function(file, extension){
            layer.load(2);
            /*if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))){
                alert('只支持jpg、png、gif格式的图片！');
                return false;
            }*/
        },
        onComplete : function(file, r){
            layer.closeAll('loading');
            if(r.code == 0){
                vm.reload();
            }else{
                alert(r.msg);
            }
        }
    });

});

var vm = new Vue({
	el:'#rapp',
	data:{
		q:{
			title: null,
            mime_type: ''
		},
		showList: true,
		title: null,
		attachment: {},
        mimeType: 'image/jpeg',
        uploadFileResource: uploadFileResource,
        baseURL: baseURL
	},
	methods: {
		query: function () {
			vm.reload();
		},
        download: function (){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
		    var url=baseURL + "/sys/attachment/download/"+id+"?X-Token="+token;
		    window.location.href=url;
        },
		getAttachment: function (attachmentId){
            $.get(baseURL + "/sys/attachment/info/"+attachmentId, function(r){
                vm.attachment = r.attachment;
                vm.mimeType = r.attachment.mimeType;
            });
        },
		info: function (){
            var id = getSelectedRow();
            if(id == null){
                return ;
            }
            vm.getAttachment(id);
            openLayer('700px', '600px', '查看附件', 'attachmentInfoLayer');
        },
		del: function () {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "/sys/attachment/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
								vm.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		reload: function () {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                postData:{'title': vm.q.title, 'mime_type': vm.q.mime_type},
                page:page
            }).trigger("reloadGrid");
		}
	}
});