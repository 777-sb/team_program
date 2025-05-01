import { createI18n } from 'vue-i18n';

const messages = {
  zh: {
    home: '首頁',
    login: '登錄',
    register: '註冊',
    vocabulary: '詞彙記憶',
    listening: '聽力訓練',
    reading: '閱讀訓練',
    settings: '設置',
    statistics: '數據統計',
    logout: '登出',
  },
  en: {
    home: 'Home',
    login: 'Login',
    register: 'Register',
    vocabulary: 'Vocabulary',
    listening: 'Listening',
    reading: 'Reading',
    settings: 'Settings',
    statistics: 'Statistics',
    logout: 'Logout',
  },
};

const i18n = createI18n({
  locale: 'zh', // 默認語言
  fallbackLocale: 'en', // 備用語言
  messages,
});

export default i18n;
