<template>
  <div>
    <h1>數據統計</h1>
    <div class="statistics">
      <div class="statistic-card">
        <h2>詞彙記憶</h2>
        <p>已學習詞彙：{{ vocabularyCount }}</p>
      </div>
      <div class="statistic-card">
        <h2>聽力訓練</h2>
        <p>已完成練習：{{ listeningCount }}</p>
      </div>
      <div class="statistic-card">
        <h2>閱讀訓練</h2>
        <p>已完成練習：{{ readingCount }}</p>
      </div>
    </div>
    <button @click="exportData">導出數據</button>
  </div>
</template>

<script>
import store from '../store';

export default {
  name: 'Statistics',
  computed: {
    vocabularyCount() {
      return store.state.vocabularyProgress.length;
    },
    listeningCount() {
      return store.state.listeningProgress.length;
    },
    readingCount() {
      return store.state.readingProgress.length;
    },
  },
  methods: {
    exportData() {
      const data = [
        ['類型', '數量'],
        ['詞彙記憶', this.vocabularyCount],
        ['聽力訓練', this.listeningCount],
        ['閱讀訓練', this.readingCount],
      ];

      const csvContent = data.map(row => row.join(',')).join('\n');
      const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = '學習數據.csv';
      link.click();
    },
  },
};
</script>

<style scoped>
.statistics {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}
.statistic-card {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  text-align: center;
  width: 200px;
}
h2 {
  color: #007bff;
}
p {
  margin: 10px 0;
}
button {
  margin-top: 20px;
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
