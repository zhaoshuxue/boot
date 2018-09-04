<template>
  <div id="app">


    <transition name="fade"
		            mode="out-in">
			<router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  methods: {
    test (){
      alert(123);
    }
  }
}
</script>

<style>
#app {
  font-family: Helvetica, sans-serif;
  text-align: center;
}
</style>
