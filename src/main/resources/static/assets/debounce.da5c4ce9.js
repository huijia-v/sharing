import{a_ as C,S as h,a$ as L}from"./base.b65bdcd1.js";var M=/\s/;function N(n){for(var r=n.length;r--&&M.test(n.charAt(r)););return r}var B=/^\s+/;function R(n){return n&&n.slice(0,N(n)+1).replace(B,"")}var S=0/0,_=/^[-+]0x[0-9a-f]+$/i,F=/^0b[01]+$/i,j=/^0o[0-7]+$/i,D=parseInt;function k(n){if(typeof n=="number")return n;if(C(n))return S;if(h(n)){var r=typeof n.valueOf=="function"?n.valueOf():n;n=h(r)?r+"":r}if(typeof n!="string")return n===0?n:+n;n=R(n);var t=F.test(n);return t||j.test(n)?D(n.slice(2),t?2:8):_.test(n)?S:+n}var H=function(){return L.Date.now()},I=H,P="Expected a function",U=Math.max,X=Math.min;function z(n,r,t){var u,c,l,s,i,f,o=0,p=!1,d=!1,T=!0;if(typeof n!="function")throw new TypeError(P);r=k(r)||0,h(t)&&(p=!!t.leading,d="maxWait"in t,l=d?U(k(t.maxWait)||0,r):l,T="trailing"in t?!!t.trailing:T);function x(e){var a=u,m=c;return u=c=void 0,o=e,s=n.apply(m,a),s}function W(e){return o=e,i=setTimeout(g,r),p?x(e):s}function O(e){var a=e-f,m=e-o,E=r-a;return d?X(E,l-m):E}function y(e){var a=e-f,m=e-o;return f===void 0||a>=r||a<0||d&&m>=l}function g(){var e=I();if(y(e))return b(e);i=setTimeout(g,O(e))}function b(e){return i=void 0,T&&u?x(e):(u=c=void 0,s)}function $(){i!==void 0&&clearTimeout(i),o=0,u=f=c=i=void 0}function A(){return i===void 0?s:b(I())}function v(){var e=I(),a=y(e);if(u=arguments,c=this,f=e,a){if(i===void 0)return W(f);if(d)return clearTimeout(i),i=setTimeout(g,r),x(f)}return i===void 0&&(i=setTimeout(g,r)),s}return v.cancel=$,v.flush=A,v}export{z as d};
