inoremap <C-e> <Esc>&
inoremap <C-a> <Esc>^
noremap <C-e> <Esc>$
noremap <C-a> <Esc>^

noremap ; :
noremap ZQ <Nop>

imap <C-r> <Plug>(processing_run)
map <C-r> <Plug>(processing_run)

inoremap <C-Right> :wnext<CR>
noremap <C-Right> :wnext<CR>


inoremap <C-Left> :wprevious<CR>
noremap <C-Left> :wprevious<CR>

noremap <C-w> :w<CR>

set fenc=utf-8
scriptencoding utf-8
set nobackup
set noswapfile
set autoread
set hidden
set showcmd
set showmatch
set number
set cursorline
set cursorcolumn
set smartindent
set autoindent
set showmatch
set wildmode=list:longest
set hlsearch
set list listchars=tab:\-\>
"set expandtab"
set tabstop=2
set shiftwidth=2
syntax on
set t_Co=256
colorscheme molokai

highlight Normal ctermbg=NONE guibg=NONE
highlight NonText ctermbg=NONE guibg=NONE
highlight SpecialKey ctermbg=NONE guibg=NONE
highlight EndOfBuffer ctermbg=NONE guibg=NONE
highlight LineNr ctermbg=none
highlight CoursorLineNr ctermbg=NONE
highlight TablineSel ctermbg=NONE
