<template>
    <div>
        <el-card>
            <div style="display: flex;justify-content: space-between;">
                <div>
                    <el-button style="padding:6px 8px" type="danger" icon="el-icon-delete" :disabled="this.multipleSelection.length===0" @click="deleteMany">删除</el-button>
                    <el-button style="padding:6px 8px" type="warning" @click="exportData" icon="fa fa-download">&nbsp;导出</el-button>
                </div>
                <el-button-group>
                    <el-tooltip effect="dark" content="隐藏/显示搜索" placement="top">
                        <el-button class="group_button" icon="fa fa-search" @click="showSearchView = !showSearchView"></el-button>
                    </el-tooltip>
                    <el-tooltip effect="dark" content="刷新" placement="top">
                        <el-button class="group_button" icon="el-icon-refresh" @click="initOperLogs"></el-button>
                    </el-tooltip>
                    <el-tooltip effect="dark" content="列" placement="top">
                        <el-popover
                                placement="bottom"
                                trigger="click"
                                popper-class="my-popover"
                                width="120">
                            <div>
                                <el-checkbox v-model="showField.showModel">系统模块</el-checkbox>
                                <el-checkbox v-model="showField.showType">操作类型</el-checkbox>
                                <el-checkbox v-model="showField.showOperator">操作人员</el-checkbox>
                                <el-checkbox v-model="showField.showIp">主机</el-checkbox>
                                <el-checkbox v-model="showField.showResult">操作结果</el-checkbox>
                                <el-checkbox v-model="showField.showGmtCreate">操作时间</el-checkbox>
                                <el-checkbox v-model="showField.showOperation">操作</el-checkbox>
                            </div>
                            <el-button class="group_button" slot="reference" style="border-top-left-radius: 0;border-bottom-left-radius: 0;" icon="fa fa-th">
                                <i class="fa fa-caret-down" aria-hidden="true" style="margin-left: 5px"></i>
                            </el-button>
                        </el-popover>
                    </el-tooltip>
                </el-button-group>
            </div>
            <el-table
                    :data="operLogs"
                    border
                    v-fit-columns
                    stripe
                    style="width: 100%;margin-top: 10px"
                    @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="日志编号"
                        sortable
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="model"
                        label="系统模块"
                        v-if="showField.showModel"
                        width="200"
                        show-overflow-tooltip>
                </el-table-column>
                <el-table-column
                        prop="type"
                        label="操作类型"
                        v-if="showField.showType"
                        width="140">
                </el-table-column>
                <el-table-column
                        prop="gmtCreate"
                        label="操作时间"
                        v-if="showField.showGmtCreate"
                        :formatter="dateFormat"
                        width="200">
                </el-table-column>
                <el-table-column
                        v-if="showField.showOperation"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="delete${pojoName}(scope.row.id)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end;margin-top: 10px">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total"
                        :total="total">
                </el-pagination>
            </div>
        </el-card>
    </div>
</template>

<script>
export default {
    name: ${pojoName},
    data() {
        return {
            ${pojoName?nocap_first}s: [],
            total: 0,
            currentPage: 1,
            size: 10,
            ${pojoName?nocap_first}Form: {
                <#list columns as column>
                "${column.propertyName}": null<#if column_has_next>, </#if>
                </#list>
            },
            showField: {
                <#list columns as column>
                "${column.propertyName}": true<#if column_has_next>, </#if>
                </#list>
            },
            multipleSelection: [],
            title: ''
        }
    },
    mounted() {
        this.initAll${pojoName}s();
    },
    methods: {
        doAddOrUpdate() {

        },
        showAddView() {

        },
        showEditView() {

        },
        deleteMany() {
            this.$confirm('此操作将永久删除【' + this.multipleSelection.length + '】条数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = '?';
                this.multipleSelection.forEach(item=>{
                    ids += 'ids=' + item.id + '&';
                });
                this.$deleteRequest('/${controllerName?uncap_first}/' + ids).then(res => {
                    if (res) {
                        this.initAll${pojoName}s();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        delete${pojoName}(id) {
            this.$confirm('此操作将永久删除【' + id + '】数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$deleteRequest('/${controllerName?uncap_first}/' + id).then(res => {
                    if (res) {
                        this.initAll${pojoName}s();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        currentChange(val) {
            this.currentPage = val;
            this.initAll${pojoName}s();
        },
        sizeChange(val) {
            this.size = val;
            this.initAll${pojoName}s();
        },
        dateFormat(row, column) {
            var date = row[column.property];
            if (date === undefined) {
                return "";
            }
            return this.$moment(date).format("YYYY-MM-DD HH:mm:ss");
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        initAll${pojoName}s() {
            let url = '/${controllerName?uncap_first}?currentPage='+this.currentPage+"&size="+this.size;
            this.$getRequest(url).then(res=>{
                if(res) {
                    this.${pojoName?nocap_first}s = res.data;
                    this.total = res.total;
                }
            })
        }
    }
}
</script>

<style scoped>

</style>
