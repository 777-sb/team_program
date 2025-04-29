<template>
  <div>
    <h1>閱讀訓練</h1>
    <div class="article">
      <h2>文章標題</h2>
      <p>這是一篇示例文章，用於閱讀訓練。你可以在此處添加更多的內容，並提供相關的閱讀理解題目。</p>
    </div>
    <div class="questions">
      <h3>問題 1：這篇文章的主題是什麼？</h3>
      <ul>
        <li v-for="(option, index) in options" :key="index">
          <label>
            <input type="radio" :value="option" v-model="selectedAnswer" />
            {{ option }}
          </label>
        </li>
      </ul>
      <button @click="checkAnswer">檢查答案</button>
      <p v-if="feedback">{{ feedback }}</p>
    </div>
  </div>
</template>

<script>
import store from '../store';

export default {
  name: 'Reading',
  data() {
    return {
      options: ['A. 科技', 'B. 教育', 'C. 健康'],
      selectedAnswer: '',
      feedback: '',
    };
  },
  methods: {
    checkAnswer() {
      if (this.selectedAnswer === 'B. 教育') {
        this.feedback = '回答正確！';
      } else {
        this.feedback = '回答錯誤，請再試一次。';
      }
      store.mutations.updateReadingProgress('問題 1');
    },
  },
};
</script>

<style scoped>
.article {
  text-align: left;
  max-width: 600px;
  margin: 0 auto;
}
.questions {
  text-align: left;
  max-width: 600px;
  margin: 20px auto;
}
button {
  margin-top: 10px;
}
</style>
