package app.picturesque.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import app.picturesque.data.model.*
import app.picturesque.data.remote.ApiResponse
import app.picturesque.data.repository.Repository
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ImageViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: Repository
    private lateinit var imageViewModel: ImageViewModel

    @Before
    fun setUp() {
        repository = mockk()
        imageViewModel = ImageViewModel(repository)
    }

    @Test
    fun `searchImages function should set the images, loading state, and error state correctly`() =
        runBlocking {
            val observer = mockk<Observer<ImageState>>(relaxed = true)
           // imageViewModel.queryImageState.observeForever(observer)

            val image1 = PhotoItem(id = "id1", url = "url1", title = "title1")
            val image2 = PhotoItem(id = "id2", url = "url2", title = "title2")
            val images = listOf(image1, image2)

            val photo1 = Photo(id = "id1", owner = "owner1", secret = "secret1", server = "server1", farm = 1, title = "title1")
            val photo2 = Photo(id = "id2", owner = "owner2", secret = "secret2", server = "server2", farm = 1, title = "title2")
            val photos = listOf(photo1,photo2)

            coEvery { repository.searchPhotos("soccer") } returns ApiResponse.Success(
                data = SearchResponse(
                    photos = Photos(page = 1, photo = photos, pages = 1, perpage = 25, total = 100),
                    stat = "stat"
                )
            )

            imageViewModel.searchImages("soccer")

            val expectedState = ImageState(loading = true, images = images)
            val slot = slot<ImageState>()
            verify(exactly = 2) { observer.onChanged(capture(slot)) }
            assert(slot.captured == expectedState)

            // Test error state
            coEvery { repository.searchPhotos("error") } returns ApiResponse.Error(message = "error")

            imageViewModel.searchImages("error")

            val expectedErrorState = ImageState(loading = true, error = "error")
            val errorSlot = slot<ImageState>()
            verify(exactly = 2) { observer.onChanged(capture(errorSlot)) }
            assert(errorSlot.captured == expectedErrorState)

            confirmVerified(observer)
        }
}

