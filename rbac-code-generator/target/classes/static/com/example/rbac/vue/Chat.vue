<template>
    <div>
        <div>
            <transition name="slide-fade">
                <el-card v-show="showSearchView">
                    <el-row>
                        <el-col :span="6">
                            员工姓名:
                            <el-input v-model="" size="small" placeholder="员工姓名" style="width: 200px"></el-input>
                        </el-col>

                        <el-col :span="6">
                            月份:
                            <el-date-picker
                                    v-model=""
                                    type="month"
                                    placeholder="选择月">
                            </el-date-picker>
                        </el-col>
                        <el-col :span="6">
                            <el-button size="mini" type="primary" icon="el-icon-search" round @click="">搜索</el-button>
                            <el-button size="mini" type="warning" icon="el-icon-refresh" round @click="">重置</el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </transition>
        </div>
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
                                                                                <el-checkbox v-model="showField.chatObj">聊天对象</el-checkbox>
                                            <el-checkbox v-model="showField.gmtCreate">创建时间</el-checkbox>
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
                        :data="chats"
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
                                label="主键id"
                                v-if="showField.id">
                        </el-table-column>
                        <el-table-column
                                prop="chatObj"
                                label="聊天对象"
                                v-if="showField.chatObj">
                        </el-table-column>
                        <el-table-column
                                prop="isDelete"
                                label="逻辑删除 1删除 0未删除"
                                v-if="showField.isDelete">
                        </el-table-column>
                        <el-table-column
                                prop="gmtCreate"
                                label="创建时间"
                                v-if="showField.gmtCreate">
                        </el-table-column>
                        <el-table-column
                                prop="gmtModified"
                                label="更新时间"
                                v-if="showField.gmtModified">
                        </el-table-column>
                    <el-table-column
                            v-if="showField.showOperation"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button type="text" size="mini" @click="showEditView(scope.row)">
                                <i class="el-icon-edit">编辑</i>
                            </el-button>
                            <el-button type="text" size="mini" @click="deleteChat(scope.row.id)">
                                <i class="el-icon-delete">删除</i>
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-dialog
                        style="border-radius: 15%"
                        :title="title"
                        width="60%"
                        :visible.sync="dialogVisible">
                    <el-form :rules="rules" ref="chatForm" :model="chatForm" label-width="100px">
                                <el-col :span="12">
                                    <el-form-item label="聊天对象" prop="chatObj">
                                        <el-input style="width: 240px;" v-model="chatForm.chatObj"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="12">
                                    <el-form-item label="创建时间" prop="gmtCreate">
                                        <el-input style="width: 240px;" v-model="chatForm.gmtCreate"></el-input>
                                    </el-form-item>
                                </el-col>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="doAddOrUpdate">确 定</el-button>
        </span>
                </el-dialog>
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
    </div>
</template>

<script>
export default {
    name: Chat,
    data() {
        return {
            showSearchView: true,
            chats: [],
            total: 0,
            currentPage: 1,
            size: 10,
            chatForm: {
                "id": null, 
                "chatObj": null, 
                "gmtCreate": null, 
            },
            showField: {
                "chatObj": true,
                "gmtCreate": true,
                "showOperation": true
            },
            multipleSelection: [],
            title: '',
            dialogVisible: false
        }
    },
    mounted() {
        this.initAllChats();
    },
    methods: {
        exportData() {
            this.$downloadRequest('/chatController/download');
        },
        doAddOrUpdate() {
            if(this.chatForm.id) {
                this.$refs['chatForm'].validate(valid=>{
                    if(valid) {
                        this.$putRequest('/chatController/',this.chatForm).then(res=>{
                            if(res) {
                                this.initAllChats();
                                this.dialogVisible = false;
                            }
                        })
                    }
                })
            } else {
                this.$refs['chatForm'].validate(valid=>{
                    if(valid) {
                        this.$postRequest('/chatController/',this.chatForm).then(res=>{
                            if(res) {
                                this.initAllChats();
                                this.dialogVisible = false;
                            }
                        })
                    }
                })
            }
        },
        showAddView() {
            this.title='添加';
            this.chatForm = {
                "id": null, 
                "chatObj": null, 
                "isDelete": null, 
                "gmtCreate": null, 
                "gmtModified": null
            };
            this.dialogVisible = true;
        },
        showEditView(data) {
            this.title = '编辑';
            Object.assign(this.chatForm, data);
            this.dialogVisible = true;
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
                this.$deleteRequest('/chatController/' + ids).then(res => {
                    if (res) {
                        this.initAllChats();
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        deleteChat(id) {
            this.$confirm('此操作将永久删除【' + id + '】数据, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                this.$deleteRequest('/chatController/' + id).then(res => {
                    if (res) {
                        this.initAllChats();
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
            this.initAllChats();
        },
        sizeChange(val) {
            this.size = val;
            this.initAllChats();
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        initAllChats() {
            let url = '/chatController/?currentPage='+this.currentPage+"&size="+this.size;
            this.$getRequest(url).then(res=>{
                if(res) {
                    this.chats = res.data;
                    this.total = res.total;
                }
            })
        }
    }
}
</script>

<style>
    .el-col .el-input,.el-select{
        margin-left: 10px;
    }
    .el-card__body .el-row {
        margin-top: 10px;
    }
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }
    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }
    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */ {
        transform: translateX(10px);
        opacity: 0;
    }

    ::v-deep .el-button-group .el-button {
        padding: 6px 12px;
        height: 30px;
        width: 46px;
    }
    .group_button {
        padding: 6px 12px;
        height: 34px;
        width: 46px;
    }
    .el-popover.my-popover{
        min-width: 120px;
    }
    .el-select {
        margin-left: -1px;
    }
</style>
