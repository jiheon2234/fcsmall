<script setup lang="ts">

import { ref } from "vue";
import axios from "axios";

const str = ref("");

//axios로 스프링 부트에 get request 보냄
axios
  .get("api/hello")
  .then((res) => {
    console.log(res.data);
    // console.log(res.data.str);
    str.value = res.data;
    console.log(str.value);
  })
  .catch((err) => {
    console.error(err);
  });
</script>

<template>
  <div class="about">
    <h1>{{ str }}</h1>

  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
