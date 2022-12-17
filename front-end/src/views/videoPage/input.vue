<template>
    <div class="input-wr">
        <div class="l">
            <img src="@/assets/icons/Avatar_Homepage_User.svg" alt="">
        </div>
        <div class="r">
            <textarea name="" id="" cols="30" rows="10" placeholder="添加评论"></textarea>
            <span></span>
            <img src="@/assets/icons/Button_Media_Sendmassage.svg" alt="">
        </div>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped lang="scss">
.input-wr{
    width: 1200px;
    padding-left: 12px;
    padding-right: 12px;
    display: flex;
    justify-content: space-between;
    .l{
        width: 30px;
        img{
            width: 30px;
            height: 30px;
        }
    }
    .r{
        display: flex;
        align-items: center;
    background: #313131;

        textarea{
            width: 1051px;
            height: 72px;
            background: #313131;
            outline: none;
            resize: none;
            border: 0px ;
            color: #fff;
        }
        img{

            width: 40px;
            height: 40px;
            margin: 0 15px;
        }
        span{
            height: 40px;
            width: 2px;
            background-color: #0E0E10;;
        }
    }
}
</style>