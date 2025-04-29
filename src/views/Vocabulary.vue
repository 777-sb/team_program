<template>
  <div class="vocabulary-card">
    <!-- 顯示當前詞彙的英文單詞 -->
    <h1>{{ currentWord.word }}</h1>
    <!-- 選項按鈕區域 -->
    <div class="options">
      <!-- 遍歷選項，生成按鈕 -->
      <button
        v-for="(option, index) in options"
        :key="index"
        @click="checkAnswer(option)"
      >
        {{ option }}
      </button>
    </div>
    <!-- 顯示反饋信息 -->
    <p v-if="feedback">{{ feedback }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Vocabulary',
  data() {
    return {
      // 詞彙數據，包含英文單詞和正確的中文翻譯
      words: [], // 從後端獲取的詞彙數據
      // 當前詞彙的索引
      currentIndex: 0,
      // 當前詞彙的選項
      options: [],
      // 反饋信息
      feedback: '',
    };
  },
  computed: {
    // 計算屬性，返回當前詞彙
    currentWord() {
      return this.words[this.currentIndex];
    },
  },
  methods: {
    async fetchWords() {
      try {
        // 替換為你的後端接口 URL
        const response = await axios.get('https://your-backend-api.com/words');
        this.words = response.data;
        this.generateOptions();
      } catch (error) {
        console.error('獲取詞彙失敗：', error);
      }
    },
    // 生成選項的方法
    generateOptions() {
      // 獲取當前詞彙的正確答案
      const correctAnswer = this.currentWord.correct;
      // 其他隨機選項
      const otherOptions = ['橘子', '西瓜', '葡萄'];
      // 將正確答案和其他選項合併，並隨機排序
      this.options = [correctAnswer, ...otherOptions].sort(() => Math.random() - 0.5);
    },
    // 檢查用戶選擇是否正確
    checkAnswer(selected) {
      if (selected === this.currentWord.correct) {
        // 如果選擇正確，顯示反饋信息並在 1 秒後進入下一個詞彙
        this.feedback = '選擇正確！';
        setTimeout(() => {
          this.nextWord();
        }, 1000);
      } else {
        // 如果選擇錯誤，顯示錯誤信息
        this.feedback = '選擇錯誤，請再試一次！';
      }
    },
    // 切換到下一個詞彙
    nextWord() {
      if (this.currentIndex < this.words.length - 1) {
        // 如果還有詞彙未完成，切換到下一個詞彙
        this.currentIndex++;
        this.generateOptions();
        this.feedback = '';
      } else {
        // 如果所有詞彙已完成，顯示完成信息
        this.feedback = '所有詞彙已完成！';
      }
    },
  },
  // 組件掛載時生成初始選項
  mounted() {
    this.fetchWords();
  },
};
</script>

<style scoped>
/* 詞彙卡片的樣式 */
.vocabulary-card {
  max-width: 400px;
  margin: 0 auto;
  text-align: center;
}

/* 詞彙標題的樣式 */
h1 {
  font-size: 2rem;
  margin-bottom: 20px;
}

/* 選項按鈕的佈局 */
.options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

/* 按鈕的樣式 */
button {
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* 按鈕的懸停效果 */
button:hover {
  background-color: #0056b3;
}

/* 反饋信息的樣式 */
p {
  margin-top: 20px;
  font-size: 1.2rem;
  color: green;
}
</style>
