/**
 * Experiment 5: Cross-Browser Compatible Interactive Webpage
 * Demonstrates: Feature detection, polyfills/fallbacks for localStorage, querySelector, and CSS features (@supports)
 */

document.addEventListener('DOMContentLoaded', () => {
    // -----------------------------------------------------------------
    // 1. SAFE STORAGE ABSTRACTION (LocalStorage Feature Detection & Fallback)
    // -----------------------------------------------------------------
    
    // In-memory storage fallback object for environments where localStorage is unavailable/disabled
    const MemoryStorageFallback = {
        _data: {},
        setItem: function(key, val) {
            this._data[key] = String(val);
        },
        getItem: function(key) {
            return this._data.hasOwnProperty(key) ? this._data[key] : null;
        },
        removeItem: function(key) {
            delete this._data[key];
        },
        clear: function() {
            this._data = {};
        }
    };

    // Feature detection function for Web Storage
    function checkLocalStorageSupport() {
        try {
            const testKey = '__test_feature_key__';
            window.localStorage.setItem(testKey, testKey);
            window.localStorage.removeItem(testKey);
            return true;
        } catch (e) {
            return false;
        }
    }

    const hasLocalStorage = checkLocalStorageSupport();
    const safeStorage = hasLocalStorage ? window.localStorage : MemoryStorageFallback;

    // Update Storage UI Diagnostics Badge
    const badgeStorage = document.getElementById('badge-storage');
    const descStorage = document.getElementById('desc-storage');
    if (hasLocalStorage) {
        badgeStorage.textContent = 'Native Supported';
        badgeStorage.className = 'badge badge-success';
        descStorage.textContent = 'Native HTML5 localStorage is active and fully functional.';
    } else {
        badgeStorage.textContent = 'Fallback Active';
        badgeStorage.className = 'badge badge-fallback';
        descStorage.textContent = 'localStorage unavailable/blocked. In-memory fallback is active.';
    }

    // -----------------------------------------------------------------
    // 2. SAFE DOM SELECTOR ABSTRACTION (querySelector Feature Detection & Fallback)
    // -----------------------------------------------------------------
    
    const hasQuerySelector = typeof document.querySelector === 'function' && typeof document.querySelectorAll === 'function';

    // Safe Cross-Browser Element Selector
    function safeFindElementsByClassName(className) {
        if (hasQuerySelector) {
            // Modern W3C Selectors API
            return Array.from(document.querySelectorAll(`.${className}`));
        } else if (typeof document.getElementsByClassName === 'function') {
            // Intermediate DOM Level 2 API
            return Array.from(document.getElementsByClassName(className));
        } else {
            // Legacy DOM Level 1 Fallback (Iterating all tags)
            const results = [];
            const allElements = document.getElementsByTagName('*');
            const regex = new RegExp('(?:^|\\s)' + className + '(?:\\s|$)');
            for (let i = 0; i < allElements.length; i++) {
                if (regex.test(allElements[i].className)) {
                    results.push(allElements[i]);
                }
            }
            return results;
        }
    }

    // Update Selector UI Diagnostics Badge
    const badgeQuery = document.getElementById('badge-query');
    const descQuery = document.getElementById('desc-query');
    if (hasQuerySelector) {
        badgeQuery.textContent = 'Native Supported';
        badgeQuery.className = 'badge badge-success';
        descQuery.textContent = 'document.querySelector & querySelectorAll are natively supported.';
    } else {
        badgeQuery.textContent = 'Legacy Fallback';
        badgeQuery.className = 'badge badge-fallback';
        descQuery.textContent = 'querySelector unavailable; using getElementsByTagName / class fallback.';
    }

    // -----------------------------------------------------------------
    // 3. CSS FEATURE DETECTION (CSS.supports)
    // -----------------------------------------------------------------
    const badgeCss = document.getElementById('badge-css');
    const descCss = document.getElementById('desc-css');

    const hasCSSSupports = typeof window.CSS !== 'undefined' && typeof window.CSS.supports === 'function';
    const isGridSupported = hasCSSSupports ? window.CSS.supports('display', 'grid') : true;

    if (isGridSupported) {
        badgeCss.textContent = 'CSS Grid Supported';
        badgeCss.className = 'badge badge-success';
        descCss.textContent = 'Modern CSS Grid active via @supports / CSS.supports().';
    } else {
        badgeCss.textContent = 'Flexbox Fallback';
        badgeCss.className = 'badge badge-warning';
        descCss.textContent = 'CSS Grid unsupported; downgraded to flexible box layout.';
    }

    // -----------------------------------------------------------------
    // 4. INTERACTIVE STORAGE DEMO ACTIONS
    // -----------------------------------------------------------------
    const storageInput = document.getElementById('storage-input');
    const storageOutput = document.getElementById('storage-output');

    document.getElementById('btn-save-storage').addEventListener('click', () => {
        const val = storageInput.value.trim();
        if (val) {
            safeStorage.setItem('exp5_demo_user_note', val);
            storageOutput.textContent = `[SUCCESS] Data saved using ${hasLocalStorage ? 'localStorage' : 'MemoryFallback'}: "${val}"`;
            storageInput.value = '';
        } else {
            alert('Please enter some text to store.');
        }
    });

    document.getElementById('btn-load-storage').addEventListener('click', () => {
        const savedVal = safeStorage.getItem('exp5_demo_user_note');
        if (savedVal !== null) {
            storageOutput.textContent = `[LOADED] Retrieved value: "${savedVal}" (Source: ${hasLocalStorage ? 'localStorage' : 'MemoryFallback'})`;
        } else {
            storageOutput.textContent = '[EMPTY] No stored data found in storage.';
        }
    });

    document.getElementById('btn-clear-storage').addEventListener('click', () => {
        safeStorage.removeItem('exp5_demo_user_note');
        storageOutput.textContent = '[CLEARED] Data cleared successfully from storage adapter.';
    });

    // -----------------------------------------------------------------
    // 5. INTERACTIVE QUERY SELECTOR FALLBACK TEST
    // -----------------------------------------------------------------
    const queryOutput = document.getElementById('query-output');
    document.getElementById('btn-query-test').addEventListener('click', () => {
        // Find elements using our robust safe abstraction
        const targets = safeFindElementsByClassName('target-item');
        
        targets.forEach((el, index) => {
            el.classList.toggle('highlighted');
        });

        queryOutput.innerHTML = `
            <strong>Safe Element Finder Result:</strong><br>
            • Found <strong>${targets.length}</strong> elements matching class <code>.target-item</code>.<br>
            • Selector engine used: <code>${hasQuerySelector ? 'document.querySelectorAll' : 'getElementsByTagName Loop Fallback'}</code>.<br>
            • Toggle state changed successfully without cross-browser exceptions.
        `;
    });
});
