import{i as x,w as K,t as U,a as H,n as I,b as T}from"./index.871a70d2.js";import{s as G,r as g,u as P,c as j,w as N,G as z,a1 as V,$ as q}from"./index.965ac11d.js";function C(e){var a;const s=P(e);return(a=s==null?void 0:s.$el)!=null?a:s}const O=x?window:void 0,A=x?window.document:void 0;x&&window.navigator;x&&window.location;function m(...e){let a,s,t,o;if(H(e[0])?([s,t,o]=e,a=O):[a,s,t,o]=e,!a)return I;let l=I;const n=N(()=>C(a),r=>{l(),r&&(r.addEventListener(s,t,o),l=()=>{r.removeEventListener(s,t,o),l=I})},{immediate:!0,flush:"post"}),i=()=>{n(),l()};return T(i),i}const L=typeof globalThis!="undefined"?globalThis:typeof window!="undefined"?window:typeof global!="undefined"?global:typeof self!="undefined"?self:{},$="__vueuse_ssr_handlers__";L[$]=L[$]||{};const Q=L[$];function X(e,a){return Q[e]||a}function Y(e){return e==null?"any":e instanceof Set?"set":e instanceof Map?"map":e instanceof Date?"date":typeof e=="boolean"?"boolean":typeof e=="string"?"string":typeof e=="object"||Array.isArray(e)?"object":Number.isNaN(e)?"any":"number"}const Z={boolean:{read:e=>e==="true",write:e=>String(e)},object:{read:e=>JSON.parse(e),write:e=>JSON.stringify(e)},number:{read:e=>Number.parseFloat(e),write:e=>String(e)},any:{read:e=>e,write:e=>String(e)},string:{read:e=>e,write:e=>String(e)},map:{read:e=>new Map(JSON.parse(e)),write:e=>JSON.stringify(Array.from(e.entries()))},set:{read:e=>new Set(JSON.parse(e)),write:e=>JSON.stringify(Array.from(e))},date:{read:e=>new Date(e),write:e=>e.toISOString()}};function ve(e,a,s,t={}){var o;const{flush:l="pre",deep:n=!0,listenToStorageChanges:i=!0,writeDefaults:r=!0,shallow:u,window:c=O,eventFilter:v,onError:h=p=>{console.error(p)}}=t,d=(u?G:g)(a);if(!s)try{s=X("getDefaultStorage",()=>{var p;return(p=O)==null?void 0:p.localStorage})()}catch(p){h(p)}if(!s)return d;const f=P(a),_=Y(f),w=(o=t.serializer)!=null?o:Z[_],{pause:E,resume:y}=K(d,()=>R(d.value),{flush:l,deep:n,eventFilter:v});return c&&i&&m(c,"storage",W),W(),d;function R(p){try{p==null?s.removeItem(e):s.setItem(e,w.write(p))}catch(S){h(S)}}function b(p){if(!(p&&p.key!==e)){E();try{const S=p?p.newValue:s.getItem(e);return S==null?(r&&f!==null&&s.setItem(e,w.write(f)),f):typeof S!="string"?S:w.read(S)}catch(S){h(S)}finally{y()}}}function W(p){p&&p.key!==e||(d.value=b(p))}}var D=Object.getOwnPropertySymbols,k=Object.prototype.hasOwnProperty,ee=Object.prototype.propertyIsEnumerable,te=(e,a)=>{var s={};for(var t in e)k.call(e,t)&&a.indexOf(t)<0&&(s[t]=e[t]);if(e!=null&&D)for(var t of D(e))a.indexOf(t)<0&&ee.call(e,t)&&(s[t]=e[t]);return s};function pe(e,a,s={}){const t=s,{window:o=O}=t,l=te(t,["window"]);let n;const i=o&&"ResizeObserver"in o,r=()=>{n&&(n.disconnect(),n=void 0)},u=N(()=>C(e),v=>{r(),i&&o&&v&&(n=new ResizeObserver(a),n.observe(v,l))},{immediate:!0,flush:"post"}),c=()=>{r(),u()};return T(c),{isSupported:i,stop:c}}const M=new Map;function me(e){const a=q();function s(i){var r;const u=M.get(e)||[];u.push(i),M.set(e,u);const c=()=>o(i);return(r=a==null?void 0:a.cleanups)==null||r.push(c),c}function t(i){function r(...u){o(r),i(...u)}return s(r)}function o(i){const r=M.get(e);if(!r)return;const u=r.indexOf(i);u>-1&&r.splice(u,1),r.length||M.delete(e)}function l(){M.delete(e)}function n(i,r){var u;(u=M.get(e))==null||u.forEach(c=>c(i,r))}return{on:s,once:t,off:o,emit:n,reset:l}}const ne=["mousedown","mouseup","keydown","keyup"];function ge(e,a={}){const{events:s=ne,document:t=A,initial:o=null}=a,l=g(o);return t&&s.forEach(n=>{m(t,n,i=>{typeof i.getModifierState=="function"&&(l.value=i.getModifierState(e))})}),l}const re={ctrl:"control",command:"meta",cmd:"meta",option:"alt",up:"arrowup",down:"arrowdown",left:"arrowleft",right:"arrowright"};function we(e={}){const{reactive:a=!1,target:s=O,aliasMap:t=re,passive:o=!0,onEventFired:l=I}=e,n=z(new Set),i={toJSON(){return{}},current:n},r=a?z(i):i,u=new Set;function c(d,f){d in r&&(a?r[d]=f:r[d].value=f)}function v(d,f){var _,w;const E=(_=d.key)==null?void 0:_.toLowerCase(),y=(w=d.code)==null?void 0:w.toLowerCase(),R=[y,E].filter(Boolean);y&&(f?n.add(d.code):n.delete(d.code));for(const b of R)c(b,f);E==="meta"&&!f?(u.forEach(b=>{n.delete(b),c(b,!1)}),u.clear()):typeof d.getModifierState=="function"&&d.getModifierState("Meta")&&f&&[...n,...R].forEach(b=>u.add(b))}s&&(m(s,"keydown",d=>(v(d,!0),l(d)),{passive:o}),m(s,"keyup",d=>(v(d,!1),l(d)),{passive:o}));const h=new Proxy(r,{get(d,f,_){if(typeof f!="string")return Reflect.get(d,f,_);if(f=f.toLowerCase(),f in t&&(f=t[f]),!(f in r))if(/[+_-]/.test(f)){const E=f.split(/[+_-]/g).map(y=>y.trim());r[f]=j(()=>E.every(y=>P(h[y])))}else r[f]=g(!1);const w=Reflect.get(d,f,_);return a?P(w):w}});return h}function he(e={}){const{touch:a=!0,drag:s=!0,initialValue:t=!1,window:o=O}=e,l=g(t),n=g(null);if(!o)return{pressed:l,sourceType:n};const i=c=>()=>{l.value=!0,n.value=c},r=()=>{l.value=!1,n.value=null},u=j(()=>C(e.target)||o);return m(u,"mousedown",i("mouse"),{passive:!0}),m(o,"mouseleave",r,{passive:!0}),m(o,"mouseup",r,{passive:!0}),s&&(m(u,"dragstart",i("mouse"),{passive:!0}),m(o,"drop",r,{passive:!0}),m(o,"dragend",r,{passive:!0})),a&&(m(u,"touchstart",i("touch"),{passive:!0}),m(o,"touchend",r,{passive:!0}),m(o,"touchcancel",r,{passive:!0})),{pressed:l,sourceType:n}}var J=Object.getOwnPropertySymbols,se=Object.prototype.hasOwnProperty,oe=Object.prototype.propertyIsEnumerable,ie=(e,a)=>{var s={};for(var t in e)se.call(e,t)&&a.indexOf(t)<0&&(s[t]=e[t]);if(e!=null&&J)for(var t of J(e))a.indexOf(t)<0&&oe.call(e,t)&&(s[t]=e[t]);return s};function ae(e,a,s={}){const t=s,{window:o=O}=t,l=ie(t,["window"]);let n;const i=o&&"MutationObserver"in o,r=()=>{n&&(n.disconnect(),n=void 0)},u=N(()=>C(e),v=>{r(),i&&o&&v&&(n=new MutationObserver(a),n.observe(v,l))},{immediate:!0}),c=()=>{r(),u()};return T(c),{isSupported:i,stop:c}}var F;(function(e){e.UP="UP",e.RIGHT="RIGHT",e.DOWN="DOWN",e.LEFT="LEFT",e.NONE="NONE"})(F||(F={}));let ue=0;function ye(e,a={}){const s=g(!1),{document:t=A,immediate:o=!0,manual:l=!1,id:n=`vueuse_styletag_${++ue}`}=a,i=g(e);let r=()=>{};const u=()=>{if(!t)return;const v=t.getElementById(n)||t.createElement("style");v.type="text/css",v.id=n,a.media&&(v.media=a.media),t.head.appendChild(v),!s.value&&(r=N(i,h=>{v.innerText=h},{immediate:!0}),s.value=!0)},c=()=>{!t||!s.value||(r(),t.head.removeChild(t.getElementById(n)),s.value=!1)};return o&&!l&&u(),l||T(c),{id:n,css:i,unload:c,load:u,isLoaded:V(s)}}function be(e=null,a={}){var s,t;const{document:o=A,observe:l=!1,titleTemplate:n="%s"}=a,i=g((s=e!=null?e:o==null?void 0:o.title)!=null?s:null);return N(i,(r,u)=>{H(r)&&r!==u&&o&&(o.title=n.replace("%s",r))},{immediate:!0}),l&&o&&ae((t=o.head)==null?void 0:t.querySelector("title"),()=>{o&&o.title!==i.value&&(i.value=n.replace("%s",o.title))},{childList:!0}),i}function le({window:e=O,initialWidth:a=1/0,initialHeight:s=1/0}={}){const t=g(a),o=g(s),l=()=>{e&&(t.value=e.innerWidth,o.value=e.innerHeight)};return l(),U(l),m("resize",l,{passive:!0}),{width:t,height:o}}const{width:B,height:ce}=le();function Se(){const e=j(()=>B.value<768),a=j(()=>B.value>=768),s=n=>{if(t(n))return"";let i="",r=-1;for(let u=0;u<n.length;u++){const c=n.charAt(u);if(c==="/"){if(r<u){let v=n.substring(r+1,u);i+=encodeURIComponent(v),r=u}i+=c}if(u===n.length-1&&r<u){let v=n.substring(r+1,u+1);i+=encodeURIComponent(v)}}return i},t=n=>n==null||n==="";return{isMobile:e,isNotMobile:a,height:ce,encodeAllIgnoreSlashes:s,strIsEmpty:t,strIsNotEmpty:n=>!t(n),removeDuplicateSeparator:n=>{let i="";n.indexOf("http://")===0?i="http://":n.indexOf("https://")===0&&(i="https://");for(let r=i.length;r<n.length-1;r++){let u=n.charAt(r),c=n.charAt(r+1);u==="/"&&c==="/"||(i+=u)}return i+=n.charAt(n.length-1),i}}}export{ve as a,be as b,ge as c,he as d,me as e,pe as f,ye as g,we as h,Se as u};
