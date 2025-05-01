import { reactive } from 'vue';
import createPersistedState from 'vuex-persistedstate';

const state = reactive({
  isAuthenticated: false,
  username: '',
  vocabularyProgress: [], // 保存詞彙記憶進度
  listeningProgress: [], // 保存聽力訓練進度
  readingProgress: [], // 保存閱讀訓練進度
});

const mutations = {
  login(username) {
    state.isAuthenticated = true;
    state.username = username;
  },
  logout() {
    state.isAuthenticated = false;
    state.username = '';
  },
  updateVocabularyProgress(word) {
    if (!state.vocabularyProgress.includes(word)) {
      state.vocabularyProgress.push(word);
    }
  },
  updateListeningProgress(question) {
    if (!state.listeningProgress.includes(question)) {
      state.listeningProgress.push(question);
    }
  },
  updateReadingProgress(question) {
    if (!state.readingProgress.includes(question)) {
      state.readingProgress.push(question);
    }
  },
};

export default {
  state,
  mutations,
  plugins: [createPersistedState()],
};
