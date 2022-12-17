<template>
    <div class="wrap">
        <h3>简介</h3>
        <p class="intro">
            I think it would be okay to touch specular on materials, but only doing so if you know the IOR. In most cases, dielectrics can be kept at .5/.425 (depends on who you ask haha), but metals should definitely have their specular changed. This is what helps bring out the tint desaturating along the Fresnel reflection.


        </p>
        <h3>公告栏</h3>
        <p class="announce">
            I think it would be okay to touch specular on materials, but only doing so if you know the IOR. In most cases, dielectrics can be kept at .5/.425 (depends on who you ask haha), but metals should definitely have their specular changed. This is what helps bring out the tint desaturating along the Fresnel reflection.
        </p>
    </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
h3{
    /* 简介 */
width: 257px;
height: 50px;
opacity: 1;
	
font-family: HarmonyOS_Sans_SC_Bold;
font-size: 15px;
font-weight: bold;
line-height: 50px;
letter-spacing: 0em;
	
color: #FFFFFF;
	
}
p.intro,
p.announce{
    width: 257px;
    font-family: HarmonyOS_Sans_Light;
font-size: 12px;
line-height: 16px;
letter-spacing: 0em;
color: #FFFFFF;

}
.wrap{
    width: 257px;
    height: calc(100vh - 100px);
    padding-right: 2px;
  overflow-y: scroll;
  box-sizing: border-box;
}
.wrap::-webkit-scrollbar {
  width: 0 !important;
}
</style>