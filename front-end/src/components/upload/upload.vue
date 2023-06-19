<template>
  <el-upload
    class="avatar-uploader"
    action="#"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload"
    :http-request="upload"
    v-model:file-list="fileList"
    :limit="1"
    :auto-upload="true"
    accept=".jpg, .jpeg, .png"
    :show-file-list="false"
  >
    <div v-if="imageUrl"  @mouseover="imgCtrl=true" @mouseout="imgCtrl=false" class="imgBox"  @click.stop="">
      <img :src="imageUrl" class="avatar" />
      <div v-show="imgCtrl" class="ctrls">
        <el-icon @click.stop="handleZoomin"><ZoomIn /></el-icon>
        <el-icon @click.stop="handleDelete"><Delete /></el-icon>
      </div>
    </div>
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    <Teleport to="body">
      <div class="imgwrap" v-if="dialogVisible">
      <el-icon class="closedialog" @click="handleVlose"><CloseBold /></el-icon>
    <img  :src="imageUrl" alt="Preview Image" />
      </div>
  </Teleport>
  </el-upload>

</template>

<script setup lang="ts">
import { postCoverImg } from "@/request/config";
import { getTokenAUTH } from "@/utils/localStorage";
import { Plus,Delete,ZoomIn,CloseBold } from "@element-plus/icons-vue";
import { ElMessage, type UploadFile, type UploadFiles, type UploadProgressEvent, type UploadProps, type UploadUserFile } from "element-plus";
import { ref } from "vue";

const imageUrl = ref("");
const imgCtrl = ref(false)
const dialogVisible = ref(false)
const handleAvatarSuccess: UploadProps["onSuccess"] = (
  response,
  uploadFile
) => {
  // imageUrl.value = URL.createObjectURL(uploadFile.raw);
  //console.log(response, uploadFile);
};

const fileList = ref([])

const beforeAvatarUpload = async () => {

};
const upload = async ()=>{
    const formData = new FormData()
    const file = (fileList.value[0] as UploadFile)!.raw
    formData.append('file', file as Blob)
    const {data:postData,status}= await postCoverImg(formData)
    if(status!==200){
        ElMessage({
            type:'warning',
            message:'上传失败'
        })
    }
    const {code,result} = postData
    if(code===0){
        imageUrl.value = result.coverUrl
        ElMessage({
            type:'success',
            message:'上传成功'
        })
    }
    //清除旧的文件列表
    fileList.value = []
}

const handleZoomin = () => {
  dialogVisible.value = true
//console.log('zoomin');
}
const handleDelete = () => {
  imageUrl.value = ''
//console.log('delete');
}
const handleVlose = () => {
  dialogVisible.value = false

}
</script>

<style scoped lang="scss">
.avatar-uploader .avatar {
    width: 88px;
    height: 88px;
    display: block;
  }
  :deep(.uploaditem .item  .el-form-item__label){
    margin-top: 6px;
  }
  .imgBox{
    position: relative;
    .ctrls{
      display: flex;
justify-content: space-around;
align-items: center;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      z-index: 1;
      background-color: rgba($color: #000000, $alpha: 0.6);
      color: #409eff;
    }
    .el-icon{
      width: 20px;
      height: 20px;
    }
  }
.imgwrap{
width: 100%;
height: 100%;
position: absolute;
top: 0;
left: 0;
display: flex;
justify-content: center;
align-items: center;
background-color: rgba(0, 0, 0, 0.7);
z-index: 150;
img{
  height: 100%;
}
.closedialog{
  position: absolute;
  top: 20px;
  right: 20px;
  background-color: #fff;
}
}
  </style>
  
  <style>
  .avatar-uploader .el-upload {
    border: 1px dashed var(--el-border-color);
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    transition: var(--el-transition-duration-fast);
  }
  
  .avatar-uploader .el-upload:hover {
    border-color: var(--el-color-primary);
  }
  
  .el-icon.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 88px;
    height: 88px;
    text-align: center;
  }


  </style>
  