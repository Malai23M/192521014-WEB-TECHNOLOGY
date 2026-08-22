/**
 * Experiment 4: Browser Information Dashboard Using Window Object
 * Demonstrates: window.location, window.innerWidth/innerHeight, window.scrollX/scrollY,
 * navigator, screen, and event listeners for resize, scroll, and online/offline.
 */

document.addEventListener('DOMContentLoaded', () => {
    // DOM References
    // Location elements
    const elLocHref = document.getElementById('loc-href');
    const elLocProto = document.getElementById('loc-protocol');
    const elLocHost = document.getElementById('loc-hostname');
    const elLocPath = document.getElementById('loc-pathname');
    const elLocPort = document.getElementById('loc-port');

    // Navigator elements
    const elNavOnline = document.getElementById('nav-online');
    const elNavLang = document.getElementById('nav-lang');
    const elNavLangs = document.getElementById('nav-langs');
    const elNavPlatform = document.getElementById('nav-platform');
    const elNavCookies = document.getElementById('nav-cookies');

    // Screen elements
    const elScreenDim = document.getElementById('screen-dim');
    const elScreenAvail = document.getElementById('screen-avail');
    const elScreenDepth = document.getElementById('screen-depth');
    const elScreenOrient = document.getElementById('screen-orient');

    // Viewport & Scroll elements
    const elViewportDim = document.getElementById('viewport-dim');
    const elScrollX = document.getElementById('scroll-x');
    const elScrollY = document.getElementById('scroll-y');
    const elDeviceDpr = document.getElementById('device-dpr');

    // 1. Function to update Location Properties
    function updateLocationInfo() {
        elLocHref.textContent = window.location.href;
        elLocProto.textContent = window.location.protocol;
        elLocHost.textContent = window.location.hostname || '(localhost/file)';
        elLocPath.textContent = window.location.pathname;
        elLocPort.textContent = window.location.port || '(default)';
    }

    // 2. Function to update Navigator Properties
    function updateNavigatorInfo() {
        const isOnline = window.navigator.onLine;
        elNavOnline.textContent = isOnline ? 'Online (Connected)' : 'Offline (Disconnected)';
        elNavOnline.className = `val status-pill ${isOnline ? 'status-online' : 'status-offline'}`;

        elNavLang.textContent = window.navigator.language || 'Unknown';
        elNavLangs.textContent = window.navigator.languages ? window.navigator.languages.join(', ') : window.navigator.language;
        elNavPlatform.textContent = window.navigator.userAgentData ? window.navigator.userAgentData.platform : (window.navigator.platform || 'Unknown');
        elNavCookies.textContent = window.navigator.cookieEnabled ? 'Enabled' : 'Disabled';
    }

    // 3. Function to update Screen Properties
    function updateScreenInfo() {
        elScreenDim.textContent = `${window.screen.width} x ${window.screen.height} px`;
        elScreenAvail.textContent = `${window.screen.availWidth} x ${window.screen.availHeight} px`;
        elScreenDepth.textContent = `${window.screen.colorDepth}-bit (${window.screen.pixelDepth}-bit pixel)`;
        elScreenOrient.textContent = window.screen.orientation ? window.screen.orientation.type : 'N/A';
    }

    // 4. Function to update Viewport and Scroll Coordinates
    function updateViewportAndScrollInfo() {
        // Viewport dimensions
        elViewportDim.textContent = `${window.innerWidth} x ${window.innerHeight} px`;
        
        // Scroll coordinates (with fallback support for pageXOffset/pageYOffset)
        const currentScrollX = Math.round(window.scrollX !== undefined ? window.scrollX : window.pageXOffset);
        const currentScrollY = Math.round(window.scrollY !== undefined ? window.scrollY : window.pageYOffset);

        elScrollX.textContent = `${currentScrollX} px`;
        elScrollY.textContent = `${currentScrollY} px`;

        // Device pixel ratio
        elDeviceDpr.textContent = `${window.devicePixelRatio || 1}x`;
    }

    // Initial population of all dashboard metrics
    updateLocationInfo();
    updateNavigatorInfo();
    updateScreenInfo();
    updateViewportAndScrollInfo();

    // Event Listeners for real-time reactivity
    // A. Window resize event
    window.addEventListener('resize', () => {
        updateViewportAndScrollInfo();
        updateScreenInfo();
    });

    // B. Window scroll event
    window.addEventListener('scroll', () => {
        updateViewportAndScrollInfo();
    });

    // C. Online / Offline network events
    window.addEventListener('online', () => {
        updateNavigatorInfo();
    });

    window.addEventListener('offline', () => {
        updateNavigatorInfo();
    });

    // Button controls for testing scrolling
    document.getElementById('btn-scroll-top').addEventListener('click', () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    });

    document.getElementById('btn-scroll-mid').addEventListener('click', () => {
        window.scrollTo({ top: 600, behavior: 'smooth' });
    });

    document.getElementById('btn-scroll-bottom').addEventListener('click', () => {
        window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
    });

    document.getElementById('btn-reload').addEventListener('click', () => {
        window.location.reload();
    });
});
