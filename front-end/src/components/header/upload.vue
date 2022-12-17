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
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
</template>

<script setup lang="ts">
import { postCoverImg } from "@/request/config";
import { getTokenAUTH } from "@/utils/localStorage";
import { Plus } from "@element-plus/icons-vue";
import { ElMessage, type UploadFile, type UploadFiles, type UploadProgressEvent, type UploadProps, type UploadUserFile } from "element-plus";
import { ref } from "vue";

const imageUrl = ref("");
const handleAvatarSuccess: UploadProps["onSuccess"] = (
  response,
  uploadFile
) => {
  // imageUrl.value = URL.createObjectURL(uploadFile.raw);
  console.log(response, uploadFile);
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

</script>

<style scoped lang="scss">
.avatar-uploader .avatar {
    width: 58px;
    height: 58px;
    display: block;
  }
  :deep(.uploaditem .item  .el-form-item__label){
    margin-top: 6px;
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
    width: 58px;
    height: 58px;
    text-align: center;
  }
  </style>
  