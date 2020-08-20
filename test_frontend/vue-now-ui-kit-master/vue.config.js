const DisURL = 'http://i3c104.p.ssafy.io:3000';
const LocalURL = 'http://localhost:3000';
var isLocal = true;

var API_URL = DisURL;
if (isLocal) {
  API_URL = LocalURL;
}

module.exports = {
  devServer: {
      proxy: {
        '/account': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
        '/board': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
        '/comment': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
        '/calender': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
        '/hashtag': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
        '/introduction': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
        '/jobpost': {
          target: API_URL,
          ws:true,
          changeOrigin:true
        },
      }
  },
  runtimeCompiler: true,
  outputDir: '../../src/main/resources/static/',

  css: {
    // Enable CSS source maps.
    sourceMap: process.env.NODE_ENV !== 'production'
  }
};
