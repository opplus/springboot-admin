$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + '/sys/article/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '标题', name: 'title', index: 'title', width: 80},
            {
                label: '缩略图', name: 'image', width: 100, formatter: function (value, options, row) {
                return '<img class="img-thumbnail" style="width: 60px;height: 60px;" src="' +  value + '" >';
            }
            },
            // { label: '正文', name: 'content', index: 'content', width: 80 },
            {label: '创建时间', name: 'createTime', index: 'create_time', width: 80},
            {label: '最近一次修改时间', name: 'updateTime', index: 'update_time', width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
    initTinymce();
});

var vm = new Vue({
    el: '#rapp',
    data: {
        showList: true,
        q: {
            keyword: null
        },
        title: null,
        article: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.article = {};
            setTinymceContent("");
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            //获取编辑器内容
            vm.article.content = getTinymceContent();
            console.info("获取编辑器内容:" + vm.article.content);
            var url = vm.article.id == null ? "/sys/article/save" : "/sys/article/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.article),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "/sys/article/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            $.get(baseURL + "/sys/article/info/" + id, function (r) {
                vm.article = r.article;
                setTinymceContent(r.article.content);
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        openAttachmentLayer: function () {
            openAttachmentLayer(vm.selectAttachment, 'image');
        },
        selectAttachment: function (data) {
            vm.article.image = data;
        },
        removeAttachment: function () {
            vm.article.image = '';
        }
    }
});