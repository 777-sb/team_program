<template>
  <div>
    <h1>聽力訓練</h1>
    <audio controls>
      <source src="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3" type="audio/mpeg">
      你的瀏覽器不支持音頻播放。
    </audio>
    <div class="questions">
      <h3>問題 1：這段音頻的主題是什麼？</h3>
      <ul>
        <li v-for="(option, index) in options" :key="index">
          <label>
            <input type="radio" :value="option" v-model="selectedAnswer" />
            {{ option }}
          </label>
        </li>
      </ul>
      <button @click="submitAnswer">提交答案</button>
      <p v-if="feedback">{{ feedback }}</p>
    </div>
  </div>
</template>

<script>
import store from '../store';

export default {
  name: 'Listening',
  data() {
    return {
      options: ['A. 音樂', 'B. 電影', 'C. 體育'],
      selectedAnswer: '',
      feedback: '',
    };
  },
  methods: {
    submitAnswer() {
      if (this.selectedAnswer === 'A. 音樂') {
        this.feedback = '回答正確！';
      } else {
        this.feedback = '回答錯誤，請再試一次。';
      }
      store.mutations.updateListeningProgress('問題 1');
    },
  },
};
</script>

<style scoped>
audio {
  width: 100%;
  margin-bottom: 20px;
}
.questions {
  text-align: left;
  max-width: 600px;
  margin: 0 auto;
}
button {
  margin-top: 10px;
}
</style>
